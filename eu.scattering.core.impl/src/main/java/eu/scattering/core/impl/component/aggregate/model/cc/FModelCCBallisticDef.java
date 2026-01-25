package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.rotate.FRotAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.ballistic.FModelCCBallistic;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.type.Center;
import eu.scattering.core.design.type.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelCCBallisticDef implements FModelCCBallistic {
    private static final int AGGREGATE_SIZE = 6;
    private static final int FRAGMENT_SIZE = 3;
    private static final int MAX_IT_GLOBAL = 10;

    private final Dimension dimension;

    private final List<BiConsumer<FAggregate, FAggregate>> monitors;
    private final List<BiFunction<FAggregate, FAggregate, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final ScatFactory factory;

    private final FRandAspect random;
    private final FRotAspect rotation;

    private final FPoint cAggA, cAggB;
    private final FRay pathA, pathB;

    private final FAggregate aggregate;

    private final List<FAggregate> fragments;

    private boolean symmetry;

    private FModelCCBallisticDef(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.dimension = dimension;

        this.monitors = new ArrayList<>();
        this.acceptors = new ArrayList<>();
        this.validators = new ArrayList<>();

        this.factory = factory;

        this.random = this.factory.getRandAspect();
        this.rotation = factory.getRotAspect();

        this.cAggA = factory.getFPoint();
        this.cAggB = factory.getFPoint();

        this.pathA = factory.getFRay();
        this.pathB = factory.getFRay();

        this.aggregate = aggregate;

        this.fragments = new ArrayList<>();

        this.symmetry = true;
    }

    public static FModelCCBallistic create(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

        return new FModelCCBallisticDef(dimension, aggregate, factory);
    }

    @Override
    public void build() {

        if (this.aggregate.getRefParticles().size() < AGGREGATE_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + AGGREGATE_SIZE + " particles");
        }

        int iteration = 0;
        int validation = 0;

        generation:
        while (iteration ++ < MAX_IT_GLOBAL) {

            init();

            while (this.fragments.size() > 1) {
                buildStepVariantSymmetry();
            }

            this.monitors.forEach(e -> e.accept(this.aggregate, null));

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

        distributeFragments();
        buildFragments();

        for (FAggregate fragment : this.fragments) {
            this.monitors.forEach(e -> e.accept(null, fragment));
        }

        shuffleFragments();
    }

    private void buildStepVariantSymmetry() {

        if (this.symmetry) {
            buildStepSymmetric();
        } else {
            buildStepRandom();
        }
    }

    private void buildStepSymmetric() {

        for (int i = 0 ; i < this.fragments.size() - 1 ; i += 2) {
            FAggregate aggA = this.fragments.get(i);
            FAggregate aggB = this.fragments.get(i + 1);

            buildStepCore(aggA, aggB);
        }

        buildStepCleanup();
    }

    private void buildStepRandom() {
        FAggregate aggA;
        FAggregate aggB;

        do {
            aggA = this.random.getFRand().getElement(this.fragments, false);
            aggB = this.random.getFRand().getElement(this.fragments, false);
        } while (aggA == aggB);

        buildStepCore(aggA, aggB);

        buildStepCleanup();
    }

    private void buildStepCore(FAggregate aggA, FAggregate aggB) {

        step:
        while (true) {
            projectVariantDimension(aggA, aggB);

            for (var acceptor : this.acceptors) {
                if (!acceptor.apply(aggA, aggB)) {

                    continue step;
                }
            }

            this.monitors.forEach(e -> e.accept(aggA, aggB));

            aggA.merge(aggB, true);

            break;
        }
    }

    private void buildStepCleanup() {

        removeFragments();
        shuffleFragments();
    }

    private void projectVariantDimension(FAggregate aggA, FAggregate aggB) {

        switch (this.dimension) {
            case D3 -> project3D(aggA, aggB);
            case D2 -> project2D(aggA, aggB);
        }
    }

    private void project3D(FAggregate aggA, FAggregate aggB) {
        FVector vectorRnd = this.pathA.getRefOrigin();
        FPoint baseRnd = vectorRnd.getRefBase();
        FPoint headRnd = vectorRnd.getRefHead();
        FVector vectorDir = this.pathB.getRefOrigin();
        FPoint baseDir = vectorDir.getRefBase();
        FPoint headDir = vectorDir.getRefHead();

        while (true) {
            aggA.getCenter(this.cAggA, Center.SPATIAL);
            aggB.getCenter(this.cAggB, Center.SPATIAL);

            double rAggA = aggA.getRadiusFrom(this.cAggA) * 0.5;
            double rAggB = aggB.getRadiusFrom(this.cAggB) * 0.5;

            double distance = 10 * (rAggA + rAggB);

            baseRnd.set(0, 0, 0);
            headRnd.set(this.random.getFRand().nextDoubleOnSphere(distance));

            aggA.getRefParticles().translate(this.cAggA, 0, 0, 0);
            aggB.getRefParticles().translate(this.cAggB, headRnd);

            this.random.ortToBaseInCircle(headDir, this.pathA, rAggA);

            vectorRnd.swapBaseWithHead();

            this.random.ortToBaseInCircle(baseDir, this.pathA, rAggB);

            double shift = aggB.project(aggA, vectorDir);

            if (shift >= 0) {
                break;
            }
        }
    }

    private void project2D(FAggregate aggA, FAggregate aggB) {
        FVector vectorRnd = this.pathA.getRefOrigin();
        FPoint baseRnd = vectorRnd.getRefBase();
        FPoint headRnd = vectorRnd.getRefHead();
        FVector vectorDir = this.pathB.getRefOrigin();
        FPoint baseDir = vectorDir.getRefBase();
        FPoint headDir = vectorDir.getRefHead();

        while (true) {
            aggA.getCenter(this.cAggA, Center.SPATIAL);
            aggB.getCenter(this.cAggB, Center.SPATIAL);

            double rAggA = aggA.getRadiusFrom(this.cAggA) * 0.5;
            double rAggB = aggB.getRadiusFrom(this.cAggB) * 0.5;

            double distance = 10 * (rAggA + rAggB);

            baseRnd.set(0, 0, 0);
            headRnd.set(this.random.getFRand().nextDoubleOnCircle(distance), 0);

            aggA.getRefParticles().translate(this.cAggA, 0, 0, 0);
            aggB.getRefParticles().translate(this.cAggB, headRnd);

            baseDir.set(this.random.getFRand().nextDouble(-rAggB, rAggB), 0, 0);
            this.rotation.setRgAngle(baseDir, headRnd, Math.PI * 0.5);

            baseDir.add(headRnd);

            headDir.set(this.random.getFRand().nextDouble(-rAggA, rAggA), 0, 0);
            this.rotation.setRgAngle(headDir, headRnd, Math.PI * 0.5);

            double shift = aggB.project(aggA, vectorDir);

            if (shift >= 0) {
                break;
            }
        }
    }

    private void distributeFragments() {

        this.fragments.clear();

        for (int i = 0; i < this.aggregate.size() / FRAGMENT_SIZE; i++) {
            this.fragments.add(this.factory.getFAggregate());
        }

        for (int i = 0 ; i < this.aggregate.size() ; i++) {
            this.fragments.get(i % this.fragments.size()).addRefParticle(this.aggregate.getRefParticles().asList().get(i));
        }
    }

    private void buildFragments() {

        for (FAggregate fragment : this.fragments) {
            factory.getFModelContext().pc().ballistic(this.dimension, fragment).build();
        }

        FBuffer<FBufferData> buffer = this.aggregate.getRefFBuffer();

        if (buffer != null) {
            for (FAggregate fragment : this.fragments) {
                fragment.setRefFBuffer(buffer);
            }
        }
    }

    private void shuffleFragments() {

        this.random.getFRand().shuffle(this.fragments);
    }

    private void removeFragments() {
        List<FAggregate> elements = this.fragments.stream().filter((fragment) -> fragment.size() > 0).toList();

        this.fragments.clear();
        this.fragments.addAll(elements);
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
    public void addStepMonitor(BiConsumer<FAggregate, FAggregate> monitor) {

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
}