package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.geometry.shape.ShapeModuleDimension;
import eu.scattering.core.design.type.RadiusOfGyration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FModelCCTunable3DDef implements FModelCCTunable {
    private static final int MAX_IT_SELECT = 100;
    private static final int MAX_IT_CORRECTION = 100;
    private static final int MAX_IT_GLOBAL = 10;
    private static final int AGGREGATE_SIZE = 10;
    private static final int FRAGMENT_SIZE = 5;

    private final List<BiConsumer<FAggregate, FAggregate>> monitors;
    private final List<BiFunction<FAggregate, FAggregate, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final ScatFactory factory;
    private final FRandAspect random;

    private final FAggregate aggregate;

    private final List<FAggregate> fragments;

    private final double kf, df;

    private boolean correction;

    private double rp;

    private FModelCCTunable3DDef(FAggregate aggregate, ScatFactory factory, double df, double kf) {

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

        this.monitors = new ArrayList<>();
        this.acceptors = new ArrayList<>();
        this.validators = new ArrayList<>();

        this.factory = factory;
        this.random = this.factory.getRandAspect();

        this.aggregate = aggregate;

        this.fragments = new ArrayList<>();

        this.df = df;
        this.kf = kf;
    }

    public static FModelCCTunable create(FAggregate aggregate, ScatFactory factory, double df, double kf) {

        return new FModelCCTunable3DDef(aggregate, factory, df, kf);
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

            while (this.fragments.size() > 1) {
                if (!buildStep()) {
                    continue generation;
                }
            }

            this.monitors.forEach(e -> e.accept(this.aggregate, null));

            for (var validator : this.validators) {
                if (validator.apply(this.aggregate, validation)) {
                    continue;
                }

                validation++;

                continue generation;
            }

            if (this.aggregate.getLinearOverlapFactor() > EPSILON) {
                continue;
            }

            return;
        }

        throw new RuntimeException("The aggregate could not be built");
    }

    private void init() {
        this.rp = getAveragedParticleRadius();

        distributeFragments();
        buildFragments();

        for (FAggregate fragment : this.fragments) {
            this.monitors.forEach(e -> e.accept(null, fragment));
        }

        shuffleFragments();
    }

    private boolean buildStep() {

        for (int i = 0 ; i < this.fragments.size() - 1 ; i += 2) {
            FAggregate aggA = this.fragments.get(i);
            FAggregate aggB = this.fragments.get(i + 1);

            int iterations = 0;

            step:
            while (true) {
                double distance = getExpectedDistance(aggA, aggB);

                this.random.moveMassCenter(aggA, aggB, distance);

                boolean isPositioned = this.random.rotate(aggA, aggB, MAX_IT_CORRECTION);

                if (!isPositioned) {
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

                this.monitors.forEach(e -> e.accept(aggA, aggB));

                aggA.merge(aggB, true);

                break;
            }
        }

        removeFragments();
        shuffleFragments();

        return true;
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

        FModelPCTunable model;
        for (FAggregate fragment : this.fragments) {
            model = factory.getFModelContext().pc().tunable(fragment, this.df, this.kf);
            model.setEarlyStageCorrection(this.correction);

            model.build();
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

    private double getAveragedParticleRadius() {

        return this.aggregate.getRefParticles().asList().stream()
                .map(ShapeModuleDimension::getRadius)
                .collect(Collectors.averagingDouble(Double::doubleValue));
    }

    private double getExpectedDistance(FAggregate aggA, FAggregate aggB) {
        int npA = aggA.size();
        int npB = aggB.size();
        double rgA = aggA.getRadiusOfGyration(RadiusOfGyration.SIMPLE_FILIPPOV);
        double rgB = aggB.getRadiusOfGyration(RadiusOfGyration.SIMPLE_FILIPPOV);

        double stepA = Math.pow((npA + npB) / kf, 2 / df) * ((npA + npB) * (npA + npB) * rp * rp) / (npA * npB);
        double stepB = ((npA + npB) * rgA * rgA) / npB;
        double stepC = ((npA + npB) * rgB * rgB) / npA;

        return Math.sqrt(stepA - stepB - stepC);
    }

    //--------------------------------------------------

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

    @Override
    public void setEarlyStageCorrection(boolean correction) {

        this.correction = correction;
    }
}