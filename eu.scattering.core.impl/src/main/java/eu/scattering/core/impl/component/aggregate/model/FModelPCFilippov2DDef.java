package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.ShapeModuleDimension;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.transfer.primitive.FPos2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class FModelPCFilippov2DDef implements FModelPCTunable {
    private static final int ITERATIONS = 100;
    private static final int MIN_SIZE = 5;

    private final List<BiConsumer<FAggregate, Shape>> monitor;
    private final List<BiFunction<FAggregate, Integer, Boolean>> acceptor;
    private final List<BiFunction<FAggregate, Shape, Boolean>> validator;

    private final FRandAspect rndEng;

    private final FAggregate aggregate;

    private final List<Shape> bases;

    private final FAssembly<Shape> attached;
    private final Queue<Shape> detached;

    private final FPoint cMass;

    private boolean correctionEarlyStage;
    private double df, kf, rp;

    private FModelPCFilippov2DDef(FAggregate aggregate, ScatFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.monitor = new ArrayList<>();
        this.acceptor = new ArrayList<>();
        this.validator = new ArrayList<>();

        this.rndEng = factory.getRandAspect();

        this.aggregate = aggregate;

        this.bases = new ArrayList<>();

        this.attached = this.aggregate.getRefParticles();
        this.detached = new LinkedList<>();

        this.cMass = factory.getFPoint();

        this.df = -1;
        this.kf = -1;
    }

    public static FModelPCTunable create(FAggregate aggregate, ScatFactory factory) {

        return new FModelPCFilippov2DDef(aggregate, factory);
    }

    public static FModelPCTunable create(FAggregate aggregate, ScatFactory factory, double df, double kf) {
        FModelPCTunable model = new FModelPCFilippov2DDef(aggregate, factory);

        model.setDf(df);
        model.setKf(kf);

        return model;
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

            this.monitor.forEach(e -> e.accept(this.aggregate, null));

            for (var acceptor : this.acceptor) {
                if (acceptor.apply(this.aggregate, iteration)) {
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

        this.detached.forEach(e -> e.setCenterZ(0));

        Shape particleA = this.detached.poll();
        assert particleA != null;

        particleA.setCenter(0, 0, 0);

        this.monitor.forEach(e -> e.accept(this.aggregate, particleA));

        this.attached.register(particleA);

        Shape particleB = this.detached.poll();
        assert particleB != null;

        FPos2D position = this.rndEng.getFRand().nextDoubleOnCircle(particleA.getRadius() + particleB.getRadius());

        this.monitor.forEach(e -> e.accept(this.aggregate, particleB));

        particleB.setCenter(position.getD0(), position.getD1(), 0);

        this.attached.register(particleB);
    }

    private boolean buildStep() {
        resetMassCenter();

        Shape particle = detached.poll();
        assert particle != null;

        double radius = getExpectedParticleDistance();

        particle.setCenter(radius, 0, 0).translate(this.cMass);
        particle.getCollisionListSpherical(this.bases, this.attached, this.cMass);

        if (this.bases.size() == 0) {
            throw new IllegalStateException("The particle cannot be attached to the aggregate");
        }

        step:
        for (int i = 0 ; i < ITERATIONS ; i++) {
            FPos2D position = this.rndEng.getFRand().nextDoubleOnCircle(radius);

            particle.setCenter(position.getD0(), position.getD1(), 0).translate(this.cMass);

            double distMin = Double.MAX_VALUE;
            Shape target = null;

            for (Shape shape : this.bases) {
                double dist = shape.getDistCenterP2(particle);

                if (dist < distMin) {
                    distMin = dist;
                    target = shape;
                }
            }

            boolean isPositioned = this.rndEng.attachSpherical2D(particle, target, this.cMass, this.attached, ITERATIONS);

            if (!isPositioned) {
                if (this.correctionEarlyStage && this.attached.size() <= MIN_SIZE) {
                   radius *= 1.01;
                }

                continue;
            }

            for (var validator : this.validator) {
                if (!validator.apply(this.aggregate, particle)) {

                    continue step;
                }
            }

            this.monitor.forEach(e -> e.accept(this.aggregate, particle));

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
        int np = this.attached.size();

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

        this.monitor.add(monitor);
    }

    @Override
    public void addStepValidator(BiFunction<FAggregate, Shape, Boolean> validator) {

        this.validator.add(validator);
    }

    @Override
    public void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator) {

        this.acceptor.add(validator);
    }

    @Override
    public boolean getEarlyStateCorrection() {

        return this.correctionEarlyStage;
    }

    @Override
    public void setEarlyStageCorrection(boolean correction) {

        this.correctionEarlyStage = correction;
    }

    @Override
    public double getDf() {

        return this.df;
    }

    @Override
    public void setDf(double df) {

        if (df <= 0) {
            throw new IllegalArgumentException("The fractal dimension must be greater than zero");
        }

        this.df = df;
    }

    @Override
    public double getKf() {

        return this.kf;
    }

    @Override
    public void setKf(double kf) {

        if (kf <= 0) {
            throw new IllegalArgumentException("The fractal prefactor must be greater than zero");
        }

        this.kf = kf;
    }


}
