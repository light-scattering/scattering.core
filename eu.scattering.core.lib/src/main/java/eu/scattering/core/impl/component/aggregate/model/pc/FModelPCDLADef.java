package eu.scattering.core.impl.component.aggregate.model.pc;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelPCDLA;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.utility.lambda.TriConsumer;
import eu.scattering.core.design.utility.type.option.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelPCDLADef implements FModelPCDLA {
    private static final int MAX_IT_INITIAL_ACCEPTOR = 1000;
    private static final int MAX_IT_GLOBAL = 10;
    private static final int MIN_SIZE = 3;

    private final Dimension dimension;

    private final List<BiConsumer<FAggregate, Shape>> monitors;
    private final List<BiFunction<FAggregate, Shape, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private TriConsumer<Shape, FRandAspect, FPoint> movement;

    private final FRandEngine rndGen;
    private final FRandAspect rndEng;

    private final FAggregate aggregate;

    private final FAssembly<Shape> attached;
    private final List<Shape> detached;

    private final FPoint center;

    private final FVector path;

    private double rAggregate, rSpawn, rExile;
    private double fSpawn, fExile, fStep;

    private boolean internal;

    private double rp;

    private FModelPCDLADef(Dimension dimension, FAggregate aggregate, ScatterFactory factory) {

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

        this.rndEng = factory.random();
        this.rndGen = this.rndEng.engine();

        this.aggregate = aggregate;

        this.attached = this.aggregate.getRefParticles();
        this.detached = new ArrayList<>(this.aggregate.size());

        this.center = factory.getFPoint();

        this.path = factory.getFVector();

        this.fExile = 4;
        this.fSpawn = 4;
        this.fStep = 1;

        setMovementVariantDimension();
    }

    public static FModelPCDLA create(Dimension dimension, FAggregate aggregate, ScatterFactory factory) {

        return new FModelPCDLADef(dimension, aggregate, factory);
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
        this.rp = this.aggregate.getFStatParticleRadius().mean();

        this.attached.register(this.detached);

        this.detached.clear();
        this.detached.addAll(this.attached.asList());

        this.attached.clear();

        initParticleA();
        initParticleB();
    }

    private void initParticleA() {
        Shape particleA = this.rndGen.getElement(this.detached, true);

        particleA.setCenter(0, 0, 0);

        this.monitors.forEach(e -> e.accept(null, particleA));
        this.attached.register(particleA);
    }

    private void initParticleB() {
        Shape particleA = this.attached.asList().get(0);
        Shape particleB = this.rndGen.getElement(this.detached, false);

        int iterations = 0;

        step:
        while (iterations++ < MAX_IT_INITIAL_ACCEPTOR) {
            initParticleVariantDimension(particleA, particleB);

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

    private void initParticleVariantDimension(Shape particleA, Shape particleB) {

        switch (this.dimension) {
            case D3 -> particleB.setCenter(this.rndGen.nextDoubleOnSphere(particleA.getRadius() + particleB.getRadius()));
            case D2 -> particleB.setCenter(this.rndGen.nextDoubleOnCircle(particleA.getRadius() + particleB.getRadius()), 0);
        }
    }

    private boolean buildStep() {
        Shape particle = this.rndGen.getElement(this.detached, false);

        adjustParameters(particle);

        main:
        while (true) {

            positionVariantDimension(particle);

            while (true) {
                this.path.set(0, 0, 0, 0, 0, 0);

                this.movement.accept(particle, this.rndEng, this.path.getRefHead());

                buildStepValidationVersionDimension();

                this.path.moveBase(particle.getRefCenter());

                particle.getRefCenter().set(this.path.getRefHead());

                if (particle.getRefCenter().getDistance(this.center) > this.rExile) {
                    continue main;
                }

                if (particle.getRefCenter().getDistance(this.center) > this.rAggregate + particle.getRadius()) {
                    continue;
                }

                if (particle.overlaps(this.attached) == 0) {
                    continue;
                }

                double distance = particle.projectFrom(this.attached, this.path, this.path.getMagnitude());

                if (distance < 0) {
                    continue;
                }

                for (var acceptor : this.acceptors) {
                    if (!acceptor.apply(this.aggregate, particle)) {

                        continue main;
                    }
                }

                this.monitors.forEach(e -> e.accept(this.aggregate, particle));
                this.attached.register(particle);
                this.detached.remove(particle);

                return true;
            }
        }
    }

    private void buildStepValidationVersionDimension() {

        if (dimension.equals(Dimension.D2)) {
            if (this.path.getRefHead().getZ() < 0 || this.path.getRefHead().getZ() > 0) {
                throw new IllegalStateException("The position of at least one particle is not 2D");
            }
        }
    }

    //--------------------------------------------------

    private void setMovementVariantDimension() {

        switch (this.dimension) {
            case D3 -> setMovement3D();
            case D2 -> setMovement2D();
        }
    }

    private void setMovement2D() {

        this.movement = (aggregate, random, position) ->
                position.set(this.rndGen.nextDoubleOnCircle(this.rp * this.fStep), 0);
    }

    private void setMovement3D() {

        this.movement = (aggregate, random, position) ->
                position.set(this.rndGen.nextDoubleOnSphere(this.rp * this.fStep));
    }

    private void positionVariantDimension(Shape particle) {

        if (this.internal) {
            switch (this.dimension) {
                case D3 -> positionInternal3D(particle);
                case D2 -> positionInternal2D(particle);
            }
        } else {
            switch (this.dimension) {
                case D3 -> positionExternal3D(particle);
                case D2 -> positionExternal2D(particle);
            }
        }
    }

    private void positionInternal2D(Shape particle) {

        do {
            particle.setCenter(this.rndGen.nextDoubleOnCircle(this.rSpawn), 0);
            particle.getRefCenter().add(this.center);
        } while (particle.overlaps(this.attached) > 0);
    }

    private void positionInternal3D(Shape particle) {

        do {
            particle.setCenter(this.rndGen.nextDoubleInSphere(this.rSpawn));
            particle.getRefCenter().add(this.center);
        } while (particle.overlaps(this.attached) > 0);
    }

    private void positionExternal2D(Shape particle) {

        particle.setCenter(this.rndGen.nextDoubleOnCircle(this.rSpawn), 0);
        particle.getRefCenter().add(this.center);
    }

    private void positionExternal3D(Shape particle) {

        particle.setCenter(this.rndGen.nextDoubleOnSphere(this.rSpawn));
        particle.getRefCenter().add(this.center);
    }

    private void adjustParameters(Shape particle) {
        this.center.set(0, 0, 0);

        for (Shape shape : this.attached) {
            this.center.add(shape.getRefCenter());
        }

        this.center.divFactor(this.attached.size());

        this.rAggregate = this.aggregate.getRadiusFrom(this.center);

        this.rSpawn = this.rAggregate + (this.rp * this.fSpawn) + particle.getRadius();
        this.rExile = this.rSpawn + (this.rp * this.fExile) + particle.getRadius();
    }

    //--------------------------------------------------

    @Override
    public TriConsumer<Shape, FRandAspect, FPoint> getMovement() {

        return this.movement;
    }

    @Override
    public void setMovement(TriConsumer<Shape, FRandAspect, FPoint> movement) {

        this.movement = movement;
    }

    @Override
    public boolean getInternalSpawn() {

        return this.internal;
    }

    @Override
    public void setInternalSpawn(boolean internal) {

        this.internal = internal;
    }

    @Override
    public double getStepFactor() {

        return this.fStep;
    }

    @Override
    public void setStepFactor(double factor) {

        if (factor <= 0) {
            throw new IllegalArgumentException("The step factor must be greater than zero");
        }

        this.fStep = factor;
    }

    @Override
    public double getSpawnFactor() {

        return this.fSpawn;
    }

    @Override
    public void setSpawnFactor(double factor) {

        if (factor <= 0) {
            throw new IllegalArgumentException("The spawn factor must be greater than zero");
        }

        this.fSpawn = factor;
    }

    @Override
    public double getExileFactor() {

        return this.fExile;
    }

    @Override
    public void setExileFactor(double factor) {

        if (factor <= 0) {
            throw new IllegalArgumentException("The exile factor must be greater than zero");
        }

        this.fExile = factor;
    }

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
}
