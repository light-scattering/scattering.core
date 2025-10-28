package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelDLA;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.lambda.TriConsumer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelDLA3DDef implements FModelDLA {
    private static final int MIN_SIZE = 5;

    private final List<BiConsumer<FAssembly<Shape>, Shape>> monitor;
    private final List<BiFunction<FAssembly<Shape>, Shape, Boolean>> validator;
    private final List<BiFunction<FAggregate, Integer, Boolean>> acceptor;

    private TriConsumer<FAssembly<Shape>, FRandEngine, FPoint> movement;

    private final FRandGenerator rndGen;
    private final FRandEngine rndEng;

    private final FAggregate aggregate;

    private final FAssembly<Shape> attached;
    private final Queue<Shape> detached;

    private final FPoint cMass;

    private final FRay dir;
    private final FPoint dirBase, dirHead;

    private double rAggregate, rSpawn, rExile;
    private double fSpawn, fExile;
    private double step;

    private FModelDLA3DDef(FAggregate aggregate, ScatFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.monitor = new ArrayList<>();
        this.validator = new ArrayList<>();
        this.acceptor = new ArrayList<>();

        this.rndEng = factory.getFRandEngine();
        this.rndGen = this.rndEng.getFRand();

        this.aggregate = aggregate;

        this.attached = factory.getFAssembly();
        this.detached = new LinkedList<>(this.aggregate.getRefParticles().asList());

        this.cMass = factory.getFPoint();

        this.dir = factory.getFRay();
        this.dirBase = this.dir.getRefOrigin().getRefBase();
        this.dirHead = this.dir.getRefOrigin().getRefHead();

        this.fSpawn = 1.5;
        this.fExile = 2.0;

        this.step = 1;

        setMovementDefault();
    }

    public static FModelDLA create(FAggregate aggregate, ScatFactory factory) {

        return new FModelDLA3DDef(aggregate, factory);
    }

    @Override
    public void build() {

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

            this.monitor.forEach(e -> e.accept(this.attached, null));
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
        this.rndGen.shuffle(this.aggregate.getRefParticles().asList());

        this.attached.clear();

        this.detached.clear();
        this.detached.addAll(this.aggregate.getRefParticles().asList());

        Shape particleA = this.detached.poll();
        assert particleA != null;

        particleA.setCenter(0, 0, 0);

        this.monitor.forEach(e -> e.accept(this.attached, particleA));

        this.attached.register(particleA);

        Shape particleB = this.detached.poll();
        assert particleB != null;

        particleB.setCenter(this.rndGen.nextDoubleOnSphere(particleA.getRadius() + particleB.getRadius()));

        this.monitor.forEach(e -> e.accept(this.attached, particleB));

        this.attached.register(particleB);
    }

    private boolean buildStep() {
        resetMassCenter();
        resetDimension();

        Shape particle = detached.poll();
        assert particle != null;

        main:
        while (true) {
            particle.setCenter(this.rndGen.nextDoubleOnSphere(this.rSpawn)).translate(this.cMass);

            step:
            while (true) {
                this.dirBase.applyStateFrom(particle.getRefCenter());
                this.dirHead.applyStateFrom(particle.getRefCenter());

                this.movement.accept(this.attached, this.rndEng, this.dirHead);

                if (this.dirHead.getDistance(this.cMass) > this.rExile + particle.getRadius()) {
                    continue main;
                }

                particle.setCenter(this.dirHead);

                if (this.dirHead.getDistance(this.cMass) > this.rAggregate + particle.getRadius()) {
                    continue;
                }

                if (particle.overlaps(this.attached) == 0) {
                    continue;
                }

                boolean isPositioned = particle.project(this.attached, this.dir, this.step);

                if (!isPositioned) {
                    continue;
                }

                for (var validator : this.validator) {
                    if (!validator.apply(this.attached, particle)) {
                        particle.setCenter(this.dirBase);

                        continue step;
                    }
                }

                this.monitor.forEach(e -> e.accept(this.attached, particle));

                this.attached.register(particle);

                return true;
            }
        }
    }

    //--------------------------------------------------

    private void setMovementDefault() {

        this.movement = (aggregate, particles, position) ->
                position.add(this.rndGen.nextDoubleOnSphere(this.step));
    }

    private void resetMassCenter() {
        this.cMass.set(0, 0, 0);

        for (Shape shape : this.attached) {
            this.cMass.add(shape.getRefCenter());
        }

        this.cMass.divFactor(this.attached.size());
    }

    private void resetDimension() {
        this.rAggregate = this.aggregate.getRadius(this.cMass);
        this.rExile = this.rAggregate * this.fExile;
        this.rSpawn = this.rAggregate * this.fSpawn;
    }

    //--------------------------------------------------

    @Override
    public void addStepMonitor(BiConsumer<FAssembly<Shape>, Shape> monitor) {

        this.monitor.add(monitor);
    }

    @Override
    public void setMovement(TriConsumer<FAssembly<Shape>, FRandEngine, FPoint> movement) {

        this.movement = movement;
    }

    @Override
    public void addStepValidator(BiFunction<FAssembly<Shape>, Shape, Boolean> validator) {

        this.validator.add(validator);
    }

    @Override
    public void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator) {

        this.acceptor.add(validator);
    }

    @Override
    public double getStep() {

        return this.step;
    }

    @Override
    public void setStep(double step) {

        if (step <= 0) {
            throw new IllegalArgumentException("The step must be greater than zero");
        }

        this.step = step;
    }

    @Override
    public double getSpawnFactor() {

        return this.fSpawn;
    }

    @Override
    public void setSpawnFactor(double factor) {

        if (factor <= 1) {
            throw new IllegalArgumentException("The spawn factor must be greater than one");
        }

        if (factor >= this.fExile) {
            throw new IllegalArgumentException("The spawn factor must be lower than the exile factor");
        }

        this.fSpawn = factor;
    }

    @Override
    public double getExileFactor() {

        return this.fExile;
    }

    @Override
    public void setExileFactor(double factor) {

        if (factor <= this.fSpawn) {
            throw new IllegalArgumentException("The exile factor must be greater than the spawn factor");
        }

        this.fExile = factor;
    }

    @Override
    public TriConsumer<FAssembly<Shape>, FRandEngine, FPoint> getMovement() {

        return this.movement;
    }
}
