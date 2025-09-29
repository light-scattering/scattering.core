package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.ShapeModuleDimension;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class FModelPCFilippov2DDef implements FModelPCTunable {
    private static final int ITERATIONS = 100;
    private static final int MIN_SIZE = 5;

    private BiConsumer<Shape, Integer> monitor;

    private final FRandEngine rndEng;

    private final FAggregate aggregate;

    private final List<Shape> bases;

    private final FAssembly<Shape> attached;
    private final Queue<Shape> detached;

    private final FPoint massCenter;

    private boolean correctionEarlyStage;
    private double df, kf, rp;

    private FModelPCFilippov2DDef(FAggregate aggregate, ScatFactory factory, double df, double kf) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        if (df <= 0) {
            throw new IllegalArgumentException("The fractal dimension must be larger than zero");
        }

        if (kf <= 0) {
            throw new IllegalArgumentException("The fractal prefactor must be larger than zero");
        }

        this.df = df;
        this.kf = kf;

        this.rndEng = factory.getFRandEngine();

        this.aggregate = aggregate;

        this.bases = new ArrayList<>();

        this.attached = factory.getFAssembly();
        this.detached = new LinkedList<>(this.aggregate.getRefParticles().asList());

        this.massCenter = factory.getFPoint();
    }

    public static FModelPCTunable create(FAggregate aggregate, ScatFactory factory, double df, double kf) {

        return new FModelPCFilippov2DDef(aggregate, factory, df, kf);
    }

    @Override
    public void build() {

        if (this.aggregate.getRefParticles().size() < MIN_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + MIN_SIZE + " particles");
        }

        init();

        while (this.detached.size() != 0) {
            if (!buildStep()) {
                throw new RuntimeException("The aggregate could not be built");
            }
        }
    }

    private void init() {
        this.rp = getAveragedParticleRadius();

        this.rndEng.getFRand().shuffle(this.aggregate.getRefParticles().asList());

        this.attached.clear();

        this.detached.clear();
        this.detached.addAll(this.aggregate.getRefParticles().asList());

        this.detached.forEach(e -> e.setCenterZ(0));

        Shape elementA = this.detached.poll();
        assert elementA != null;

        elementA.setCenter(0, 0, 0);

        this.attached.register(elementA);

        Shape elementB = this.detached.poll();
        assert elementB != null;

        FPos2D position = this.rndEng.getFRand().nextDoubleOnCircle(elementA.getRadius() + elementB.getRadius());

        elementB.setCenter(position.getD0(), position.getD1(), 0);

        this.attached.register(elementB);
    }

    private boolean buildStep() {
        resetMassCenter();

        Shape particle = detached.poll();
        assert particle != null;

        double radius = getExpectedParticleDistance();

        particle.setCenter(radius, 0, 0);
        particle.getCollisionListSpherical(this.bases, this.attached, this.massCenter);

        if (this.bases.size() == 0) {
            throw new IllegalStateException("The particle cannot be attached to the aggregate");
        }

        for (int i = 0 ; i < ITERATIONS ; i++) {
            FPos2D position = this.rndEng.getFRand().nextDoubleOnCircle(radius);

            particle.setCenter(position.getD0(), position.getD1(), 0);

            double distMin = Double.MAX_VALUE;
            Shape target = null;

            for (Shape shape : this.bases) {
                double dist = shape.getDistCenterP2(particle);

                if (dist < distMin) {
                    distMin = dist;
                    target = shape;
                }
            }

            boolean isPositioned = this.rndEng.attachSpherical2D(particle, target, 0, 0, 0, this.attached, ITERATIONS);

            if (!isPositioned) {
                if (this.correctionEarlyStage && this.attached.size() <= MIN_SIZE) {
                   radius *= 1.01;
                }

                continue;
            }

            if (this.monitor != null) {
                this.monitor.accept(particle, this.attached.size());
            }

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
        this.massCenter.set(0, 0, 0);

        for (Shape shape : this.attached) {
            this.massCenter.add(shape.getRefCenter());
        }

        this.massCenter.divFactor(this.attached.size());

        this.attached.forEach(e -> e.translate(-massCenter.getX(), -massCenter.getY(), -massCenter.getZ()));

        this.massCenter.set(0, 0, 0);
    }

    //--------------------------------------------------

    @Override
    public void addMonitor(BiConsumer<Shape, Integer> monitor) {

        this.monitor = monitor;
    }

    @Override
    public void setEarlyStageCorrection(boolean correction) {

        this.correctionEarlyStage = correction;
    }

    @Override
    public void setDf(double df) {

        if (df <= 0) {
            throw new IllegalArgumentException("The fractal dimension must be greater than zero");
        }

        this.df = df;
    }

    @Override
    public void setKf(double kf) {

        if (kf <= 0) {
            throw new IllegalArgumentException("The fractal prefactor must be greater than zero");
        }

        this.kf = kf;
    }
}
