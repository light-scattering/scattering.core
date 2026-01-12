package eu.scattering.core.impl.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.ShapeModuleDimension;
import eu.scattering.core.design.aspect.randomize.FRandAspect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class FModelPCFilippov3DDef implements FModelPCTunable {
    private static final int ITERATIONS = 100;
    private static final int MIN_SIZE = 5;

    private final List<BiConsumer<FAggregate, Shape>> monitors;
    private final List<BiFunction<FAggregate, Shape, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final FRandAspect rndEng;

    private final FAggregate aggregate;

    private final List<Shape> bases;

    private final FAssembly<Shape> attached;
    private final Queue<Shape> detached;

    private final FPoint cMass;

    private final double kf, df;

    private boolean correctionEarlyStage;

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

        this.rndEng = factory.getRandAspect();

        this.aggregate = aggregate;

        this.bases = new ArrayList<>();

        this.attached = this.aggregate.getRefParticles();
        this.detached = new LinkedList<>();

        this.cMass = factory.getFPoint();

        this.df = df;
        this.kf = kf;
    }

    public static FModelPCTunable create(FAggregate aggregate, ScatFactory factory, double df, double kf) {

        return new FModelPCFilippov3DDef(aggregate, factory, df, kf);
    }

    @Override
    public void build() {

        if (this.df < 0) {
            throw new IllegalStateException("The fractal dimension is not defined");
        }

        if (this.kf < 0) {
            throw new IllegalStateException("The fractal prefactor is not defined");
        }

        if (this.aggregate.getRefParticles().size() < MIN_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + MIN_SIZE + " particles");
        }


        boolean loop;
        int iteration = 0;

        do {
            loop = false;

            init();

            while (this.detached.size() != 0) {
                if (!buildStep()) {
                    throw new RuntimeException("The aggregate could not be built");
                }
            }

            this.monitors.forEach(e -> e.accept(this.aggregate, null));

            for (var validator : this.validators) {
                if (validator.apply(this.aggregate, iteration)) {
                    continue;
                }

                iteration++;
                loop = true;

                break;
            }

        } while (loop);
    }

    private void init() {
        this.rp = getAveragedParticleRadius();

        this.rndEng.getFRand().shuffle(this.aggregate.getRefParticles().asList());

        this.detached.clear();
        this.detached.addAll(this.attached.asList());

        this.attached.clear();

        Shape particleA = this.detached.poll();
        assert particleA != null;

        particleA.setCenter(0, 0, 0);

        this.monitors.forEach(e -> e.accept(this.aggregate, particleA));

        this.attached.register(particleA);

        Shape particleB = this.detached.poll();
        assert particleB != null;

        particleB.setCenter(this.rndEng.getFRand().nextDoubleOnSphere(particleA.getRadius() + particleB.getRadius()));

        this.monitors.forEach(e -> e.accept(this.aggregate, particleB));

        this.attached.register(particleB);
    }

    private boolean buildStep() {
        resetMassCenter();

        Shape particle = detached.poll();
        assert particle != null;

        double radius = getExpectedParticleDistance();

        particle.setCenter(radius, 0, 0).translate(this.cMass);
        particle.getCollisionsSpherical(this.bases, this.attached, this.cMass);

        if (this.bases.size() == 0) {
            throw new IllegalStateException("The particle cannot be attached to the aggregate");
        }

        step:
        for (int i = 0 ; i < ITERATIONS ; i++) {
            particle.setCenter(this.rndEng.getFRand().nextDoubleOnSphere(radius)).translate(this.cMass);

            double distMin = Double.MAX_VALUE;
            Shape target = null;

            for (Shape shape : this.bases) {
                double dist = shape.getDistCenterP2(particle);

                if (dist < distMin) {
                    distMin = dist;
                    target = shape;
                }
            }

            boolean isPositioned = this.rndEng.attachSpherical(particle, target, this.cMass, this.attached, ITERATIONS);

            if (!isPositioned) {
                if (this.correctionEarlyStage && this.attached.size() <= MIN_SIZE) {
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

            return true;
        }

        return false;
    }

    private double getAveragedParticleRadius() {

        return this.aggregate.getRefParticles().asList().stream()
                .map(ShapeModuleDimension::getRadius)
                .collect(Collectors.averagingDouble(Double::doubleValue));
    }

    private double getExpectedParticleDistance() {
        int np = this.attached.size() + 1;

        double stepA = Math.pow(np / kf, 2 / df) * (np * np * rp * rp) / (np - 1);
        double stepB = (np * rp * rp) / (np - 1);
        double stepC = Math.pow((np - 1) / kf, 2 / df) * (np * rp * rp);

        return Math.sqrt(stepA - stepB - stepC);

    }

    private void resetMassCenter() {
        this.cMass.set(0, 0, 0);

        for (Shape shape : this.attached) {
            this.cMass.add(shape.getRefCenter());
        }

        this.cMass.divFactor(this.attached.size());
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

        this.correctionEarlyStage = correction;
    }
}
