package eu.scattering.core.impl.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelPCDLA;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.lambda.TriConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelPCDLA3DDef implements FModelPCDLA {
    private static final int MAX_IT_INITIAL_ACCEPTOR = 1000;
    private static final int MAX_IT_GLOBAL = 10;
    private static final int MIN_SIZE = 3;

    private final List<BiConsumer<FAggregate, Shape>> monitors;
    private final List<BiFunction<FAggregate, Shape, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private TriConsumer<FAssembly<Shape>, FRandAspect, FPoint> movement;

    private final FRandGenerator rndGen;
    private final FRandAspect rndEng;

    private final FAggregate aggregate;

    private final FAssembly<Shape> attached;
    private final List<Shape> detached;

    private final FPoint center;

    private final FRay dir;
    private final FPoint dirBase, dirHead;

    private double rAggregate, rSpawn, rExile;
    private double fSpawn, fExile, fStep;

    private boolean internal;

    private double rp;

    private FModelPCDLA3DDef(FAggregate aggregate, ScatFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.monitors = new ArrayList<>();
        this.acceptors = new ArrayList<>();
        this.validators = new ArrayList<>();

        this.rndEng = factory.getRandAspect();
        this.rndGen = this.rndEng.getFRand();

        this.aggregate = aggregate;

        this.attached = this.aggregate.getRefParticles();
        this.detached = new ArrayList<>(this.aggregate.size());

        this.center = factory.getFPoint();

        this.dir = factory.getFRay();
        this.dirBase = this.dir.getRefOrigin().getRefBase();
        this.dirHead = this.dir.getRefOrigin().getRefHead();

        this.fExile = 4;
        this.fSpawn = 4;
        this.fStep = 1;

        setMovementDefault();
    }

    public static FModelPCDLA create(FAggregate aggregate, ScatFactory factory) {

        return new FModelPCDLA3DDef(aggregate, factory);
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
            particleB.setCenter(this.rndGen.nextDoubleOnSphere(particleA.getRadius() + particleB.getRadius()));

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

        Shape particle = this.rndGen.getElement(this.detached, false);

        resetDimension(particle);

        main:
        while (true) {
            if (this.internal) {
                particle.setCenter(this.rndGen.nextDoubleInSphere(this.rSpawn)).translate(this.center);

                if (particle.overlaps(this.attached) > 0) {
                    continue;
                }
            } else {
                particle.setCenter(this.rndGen.nextDoubleOnSphere(this.rSpawn)).translate(this.center);
            }

            step:
            while (true) {
                this.dirBase.set(particle.getRefCenter());
                this.dirHead.set(particle.getRefCenter());

                this.movement.accept(this.attached, this.rndEng, this.dirHead);

                if (this.dirHead.getDistance(this.center) > this.rExile) {
                    continue main;
                }

                particle.setCenter(this.dirHead);

                if (this.dirHead.getDistance(this.center) > this.rAggregate + particle.getRadius()) {
                    continue;
                }

                if (particle.overlaps(this.attached) == 0) {
                    continue;
                }

                double distance = particle.projectFrom(this.attached, this.dir, this.rp * this.fStep);

                if (distance < 0) {
                    continue;
                }

                for (var acceptor : this.acceptors) {
                    if (!acceptor.apply(this.aggregate, particle)) {
                        particle.setCenter(this.dirBase);

                        continue step;
                    }
                }

                this.monitors.forEach(e -> e.accept(this.aggregate, particle));
                this.attached.register(particle);
                this.detached.remove(particle);

                return true;
            }
        }
    }

    //--------------------------------------------------

    private void setMovementDefault() {

        this.movement = (aggregate, random, position) ->
                position.add(this.rndGen.nextDoubleOnSphere(this.rp * this.fStep));
    }

    private void resetMassCenter() {
        this.center.set(0, 0, 0);

        for (Shape shape : this.attached) {
            this.center.add(shape.getRefCenter());
        }

        this.center.divFactor(this.attached.size());
    }

    private void resetDimension(Shape particle) {
        this.rAggregate = this.aggregate.getRadius(this.center);
        this.rSpawn = this.rAggregate + (this.rp * this.fSpawn) + particle.getRadius();
        this.rExile = this.rSpawn + (this.rp * this.fExile) + particle.getRadius();
    }

    //--------------------------------------------------

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
    public TriConsumer<FAssembly<Shape>, FRandAspect, FPoint> getMovement() {

        return this.movement;
    }

    @Override
    public void setMovement(TriConsumer<FAssembly<Shape>, FRandAspect, FPoint> movement) {

        this.movement = movement;
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
