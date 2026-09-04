package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.utility.lambda.TriConsumer;
import eu.scattering.core.design.utility.type.option.Dimension;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FModelCCTunableDef implements FModelCCTunable {
    private static final int MAX_IT_SELECT = 100;
    private static final int MAX_IT_CORRECTION = 100;
    private static final int MAX_IT_GLOBAL = 10;
    private static final int AGGREGATE_SIZE = 10;
    private static final int FRAGMENT_SIZE = 5;

    private final Dimension dimension;

    private final List<Consumer<FAggregate>> viewers;
    private final List<TriConsumer<FAggregate, FAggregate, Integer>> monitors;
    private final List<BiFunction<FAggregate, FAggregate, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final ScatterFactory factory;
    private final FRandAspect random;

    private final FAggregate aggregate;

    private final List<FAggregate> fragments;

    private final FPoint centerTmp, centerA, centerB;

    private final double kf, df;

    private boolean correction;
    private boolean correctionEarly;

    private double rp;

    private boolean symmetry;

    private FModelCCTunableDef(Dimension dimension, FAggregate aggregate, ScatterFactory factory, double df, double kf) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        if (df <= 0) {
            throw new IllegalArgumentException("The fractal dimension must be greater than zero");
        }

        if (kf <= 0) {
            throw new IllegalArgumentException("The fractal prefactor must be greater than zero");
        }

        this.dimension = dimension;

        this.viewers = new ArrayList<>();
        this.monitors = new ArrayList<>();
        this.acceptors = new ArrayList<>();
        this.validators = new ArrayList<>();

        this.factory = factory;
        this.random = this.factory.random();

        this.aggregate = aggregate;

        this.fragments = new ArrayList<>();

        this.centerTmp = this.factory.getFPoint();
        this.centerA = this.factory.getFPoint();
        this.centerB = this.factory.getFPoint();

        this.df = df;
        this.kf = kf;

        this.symmetry = true;
    }

    public static FModelCCTunable create(Dimension dimension, FAggregate aggregate, ScatterFactory factory, double df, double kf) {

        return new FModelCCTunableDef(dimension, aggregate, factory, df, kf);
    }

    @Override
    public void build() {

        if (this.df < 0) {
            throw new IllegalStateException("The fractal dimension is not defined");
        }

        if (this.kf < 0) {
            throw new IllegalStateException("The fractal prefactor is not defined");
        }

        if (this.aggregate.getRefParticles().size() < AGGREGATE_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + AGGREGATE_SIZE + " particles");
        }

        int iteration = 0;
        int validation = 0;

        generation:
        while (iteration ++ < MAX_IT_GLOBAL) {

            init();

            AtomicInteger index = new AtomicInteger(0);
            while (this.fragments.size() > 1) {
                if (!buildStepVariantSymmetry(index)) {
                    continue generation;
                }
            }

            for (var monitor : this.monitors) {
                monitor.accept(this.aggregate, null, index.get());
            }

            for (var validator : this.validators) {
                if (validator.apply(this.aggregate, validation)) {
                    continue;
                }

                validation++;

                continue generation;
            }

            return;
        }

        throw new RuntimeException("The aggregate could not be built");
    }

    private void init() {
        this.rp = this.aggregate.getFStatParticleRadius().mean();

        createFragments();
        buildFragments();

        for (FAggregate fragment : this.fragments) {
            this.viewers.forEach(e -> e.accept(fragment));
        }

        shuffleFragments();
    }

    private boolean buildStepVariantSymmetry(AtomicInteger index) {

        if (this.symmetry) {
            return buildStepSymmetric(index);
        } else {
            return buildStepRandom(index);
        }
    }

    private boolean buildStepSymmetric(AtomicInteger index) {

        for (int i = 0 ; i < this.fragments.size() - 1 ; i += 2) {
            FAggregate aggA = this.fragments.get(i);
            FAggregate aggB = this.fragments.get(i + 1);

            boolean proceed = buildStepCore(aggA, aggB, index);

            if (!proceed) {
                return false;
            }
        }

        removeFragments();
        shuffleFragments();

        return true;
    }

    private boolean buildStepRandom(AtomicInteger index) {
        FAggregate aggA;
        FAggregate aggB;

        do {
            aggA = this.random.engine().getElement(this.fragments, false);
            aggB = this.random.engine().getElement(this.fragments, false);
        } while (aggA == aggB);

        boolean proceed = buildStepCore(aggA, aggB, index);

        if (!proceed) {
            return false;
        }

        buildStepCleanup();

        return true;
    }

    private boolean buildStepCore(FAggregate aggA, FAggregate aggB, AtomicInteger index) {
        double distance = getMassCenterDistance(aggA, aggB);

        int iterations = 0;

        step:
        while (true) {

            if (!validateBuildStep(aggA, aggB, distance)) {
                if (this.correction) {
                    distance *= 0.99;

                    continue;
                }

                return false;
            }

            setCenter(aggA, aggB);
            moveCenter(aggB, distance);

            boolean isPositioned = rotateVariantDimension(aggA, aggB);

            if (!isPositioned) {

                iterations++;

                if (iterations++ < MAX_IT_SELECT) {
                    continue;
                }

                return false;
            }

            for (var acceptor : this.acceptors) {
                if (!acceptor.apply(aggA, aggB)) {

                    iterations++;

                    if (iterations++ < MAX_IT_SELECT) {
                        continue step;
                    }

                    return false;
                }
            }

            for (var monitor : this.monitors) {
                monitor.accept(aggA, aggB, index.get());
            }

            index.set(index.get() + 1);

            aggA.merge(aggB, true);

            break;
        }

        return true;
    }

    private void buildStepCleanup() {

        removeFragments();
        shuffleFragments();
    }

    private boolean rotateVariantDimension(FAggregate aggA, FAggregate aggB) {

        return switch (this.dimension) {
            case D3 -> this.random.mutate().rotate(aggA, aggB, this.centerA, this.centerB, MAX_IT_CORRECTION);
            case D2 -> this.random.mutate().rotateOnPlane(aggA, aggB, this.centerA, this.centerB, MAX_IT_CORRECTION);
        };
    }

    private void createFragments() {

        this.fragments.clear();

        for (int i = 0; i < this.aggregate.size() / FRAGMENT_SIZE; i++) {
            this.fragments.add(this.factory.getFAggregate());
        }

        List<Shape> particles = this.aggregate.getRefParticles().asList();

        this.random.engine().shuffle(particles);

        for (int i = 0 ; i < particles.size() ; i++) {
            this.fragments.get(i % this.fragments.size()).addRefParticle(particles.get(i));
        }
    }

    private void removeFragments() {
        List<FAggregate> elements = this.fragments.stream().filter((fragment) -> fragment.size() > 0).toList();

        this.fragments.clear();
        this.fragments.addAll(elements);
    }

    private void shuffleFragments() {

        this.random.engine().shuffle(this.fragments);
    }

    private void buildFragments() {

        for (FAggregate fragment : this.fragments) {
            FModelPCTunable model = factory.models().pc().tunable(this.dimension, fragment, this.df, this.kf);
            model.setEarlyStageCorrection(this.correctionEarly);

            model.build();
        }

        FBuffer<FBufferData> buffer = this.aggregate.getRefFExtension().getRefFBuffer();

        if (buffer != null) {
            for (FAggregate fragment : this.fragments) {
                fragment.setRefFBuffer(buffer);
            }
        }
    }

    private double getMassCenterDistance(FAggregate aggA, FAggregate aggB) {
        int npA = aggA.size();
        int npB = aggB.size();
        double rgA = aggA.getRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);
        double rgB = aggB.getRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);

        double stepA = Math.pow((npA + npB) / kf, 2 / df) * ((npA + npB) * (npA + npB) * rp * rp) / (npA * npB);
        double stepB = ((npA + npB) * rgA * rgA) / npB;
        double stepC = ((npA + npB) * rgB * rgB) / npA;

        return Math.sqrt(stepA - stepB - stepC);
    }

    private void setCenter(FAggregate aggA, FAggregate aggB) {
        setCenterSingle(aggA, this.centerA);
        setCenterSingle(aggB, this.centerB);
    }

    private void setCenterSingle(FAggregate aggregate, FPoint center) {
        center.set(0, 0, 0);

        for (Shape shape : aggregate) {
            center.add(shape.getRefCenter());
        }

        center.divFactor(aggregate.size());
    }

    private void moveCenter(FAggregate aggB, double distance) {
        this.centerTmp.set(this.centerA);

        moveCenterVariantDimension(distance);

        aggB.getRefParticles().translate(this.centerB, this.centerTmp);

        this.centerB.set(this.centerTmp);
    }

    private void moveCenterVariantDimension(double distance) {

        switch (this.dimension) {
            case D3 -> this.centerTmp.add(this.random.engine().nextDoubleOnSphere(distance));
            case D2 -> this.centerTmp.add(this.random.engine().nextDoubleOnCircle(distance), 0);
        }
    }

    private boolean validateBuildStep(FAggregate aggA, FAggregate aggB, double distance) {
        double radiusA = aggA.getRadiusFrom(this.centerA);
        double radiusB = aggB.getRadiusFrom(this.centerB);

        return distance < radiusA + radiusB;
    }

    //--------------------------------------------------

    @Override
    public boolean getSymmetry() {

        return this.symmetry;
    }

    @Override
    public void setSymmetry(boolean symmetry) {

        this.symmetry = symmetry;
    }

    @Override
    public void addFragmentViewer(Consumer<FAggregate> viewer) {

        this.viewers.add(viewer);
    }

    @Override
    public void addStepMonitor(TriConsumer<FAggregate, FAggregate, Integer> monitor) {

        this.monitors.add(monitor);
    }

    @Override
    public void addStepAcceptor(BiFunction<FAggregate, FAggregate, Boolean> acceptor) {

        this.acceptors.add(acceptor);
    }

    @Override
    public void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator) {

        this.validators.add(validator);
    }

    @Override
    public boolean getCorrection() {

        return this.correction;
    }

    @Override
    public void setCorrection(boolean correction) {

        this.correction = correction;
    }

    @Override
    public boolean getEarlyStageCorrection() {

        return this.correctionEarly;
    }

    @Override
    public void setEarlyStageCorrection(boolean correction) {

        this.correctionEarly = correction;
    }
}