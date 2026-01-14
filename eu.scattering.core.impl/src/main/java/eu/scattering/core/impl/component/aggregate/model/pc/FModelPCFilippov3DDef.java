package eu.scattering.core.impl.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelPCFilippov3DDef implements FModelPCTunable {
    private static final int MAX_IT_INITIAL_ACCEPTOR = 1000;
    private static final int MAX_IT_SELECT = 100;
    private static final int MAX_IT_CORRECTION = 100;
    private static final int MAX_IT_GLOBAL = 10;
    private static final int MIN_SIZE = 5;

    private final List<BiConsumer<FAggregate, Shape>> monitors;
    private final List<BiFunction<FAggregate, Shape, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final FRandAspect random;

    private final FAggregate aggregate;

    private final FAssembly<Shape> attached;
    private final List<Shape> detached;

    private final List<Shape> bases;

    private final FPoint center;

    private final double kf, df;

    private boolean correction;

    private double rp;

    private FModelPCFilippov3DDef(FAggregate aggregate, ScatFactory factory, double df, double kf) {

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

        this.random = factory.getRandAspect();

        this.aggregate = aggregate;

        this.bases = new ArrayList<>();

        this.attached = this.aggregate.getRefParticles();
        this.detached = new ArrayList<>(this.aggregate.size());

        this.center = factory.getFPoint();

        this.df = df;
        this.kf = kf;
    }

    public static FModelPCTunable create(FAggregate aggregate, ScatFactory factory, double df, double kf) {

        return new FModelPCFilippov3DDef(aggregate, factory, df, kf);
    }

    @Override
    public void build() {

        if (this.aggregate.getRefParticles().size() < MIN_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + MIN_SIZE + " particles");
        }

        int iteration = 0;
        int validation = 0;

        generation:
        while (iteration ++ < MAX_IT_GLOBAL) {

            init();

            while (this.detached.size() != 0) {
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

            return;
        }

        throw new RuntimeException("The aggregate could not be built");
    }

    private void init() {
        this.attached.register(this.detached);

        this.rp = this.aggregate.getFStatParticleRadius().mean();

        this.detached.clear();
        this.detached.addAll(this.attached.asList());

        this.attached.clear();

        initParticleA();
        initParticleB();
    }

    private void initParticleA() {
        Shape particleA = this.random.getFRand().getElement(this.detached, true);

        particleA.setCenter(0, 0, 0);

        this.monitors.forEach(e -> e.accept(null, particleA));
        this.attached.register(particleA);
    }

    private void initParticleB() {
        Shape particleA = this.attached.asList().get(0);
        Shape particleB = this.random.getFRand().getElement(this.detached, false);

        int iterations = 0;

        step:
        while (iterations++ < MAX_IT_INITIAL_ACCEPTOR) {
            particleB.setCenter(this.random.getFRand().nextDoubleOnSphere(particleA.getRadius() + particleB.getRadius()));

            for (var acceptor : this.acceptors) {
                if (!acceptor.apply(this.aggregate, particleB)) {

                    continue step;
                }
            }

            this.monitors.forEach(e -> e.accept(this.aggregate, particleB));
            this.attached.register(particleB);
            this.detached.remove(particleB);

            return;
        }

        throw new IllegalStateException("The acceptor prevents the structure from being generated");
    }

    private boolean buildStep() {
        resetMassCenter();

        Shape particle = this.random.getFRand().getElement(this.detached, false);

        double radius = getExpectedDistance();

        particle.setCenter(radius, 0, 0).translate(this.center);
        particle.getCollisionsSpherical(this.bases, this.attached, this.center);

        if (this.bases.size() == 0) {
            throw new IllegalStateException("The particle cannot be attached to the aggregate");
        }

        step:
        for (int i = 0; i < MAX_IT_SELECT; i++) {
            particle.setCenter(this.random.getFRand().nextDoubleOnSphere(radius)).translate(this.center);

            double distMin = Double.MAX_VALUE;
            Shape target = null;

            for (Shape shape : this.bases) {
                double dist = shape.getDistCenterP2(particle);

                if (dist < distMin) {
                    distMin = dist;
                    target = shape;
                }
            }

            if (target == null) {
                return false;
            }

            boolean isPositioned = this.random.attachSpherical(particle, target, this.center, this.attached, MAX_IT_CORRECTION);

            if (!isPositioned) {
                if (this.correction && this.attached.size() <= MIN_SIZE) {
                   radius *= 1.01;
                }

                continue;
            }

            for (var acceptor : this.acceptors) {
                if (!acceptor.apply(this.aggregate, particle)) {

                    continue step;
                }
            }

            this.monitors.forEach(e -> e.accept(this.aggregate, particle));
            this.attached.register(particle);
            this.detached.remove(particle);

            return true;
        }

        return false;
    }

    private double getExpectedDistance() {
        int np = this.attached.size() + 1;

        double stepA = Math.pow(np / kf, 2 / df) * (np * np * rp * rp) / (np - 1);
        double stepB = (np * rp * rp) / (np - 1);
        double stepC = Math.pow((np - 1) / kf, 2 / df) * (np * rp * rp);

        return Math.sqrt(stepA - stepB - stepC);
    }

    private void resetMassCenter() {
        this.center.set(0, 0, 0);

        for (Shape shape : this.attached) {
            this.center.add(shape.getRefCenter());
        }

        this.center.divFactor(this.attached.size());
    }

    //--------------------------------------------------

    @Override
    public void addStepMonitor(BiConsumer<FAggregate, Shape> monitor) {

        this.monitors.add(monitor);
    }

    @Override
    public void addStepAcceptor(BiFunction<FAggregate, Shape, Boolean> acceptor) {

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
