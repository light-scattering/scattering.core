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
import eu.scattering.core.design.util.lambda.TriConsumer;
import eu.scattering.core.design.util.lambda.TriFunction;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiConsumer;

public class FModelDLA3DDef implements FModelDLA {
    private static final int MIN_SIZE = 5;

    private BiConsumer<Shape, Integer> monitor;

    private TriConsumer<FAssembly<Shape>, FRandEngine, FPoint> movement;
    private TriFunction<FAssembly<Shape>, FRandEngine, Shape, Boolean> validation;

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

        init();

        while (this.detached.size() != 0) {
            if (!buildStep()) {
                throw new RuntimeException("The aggregate could not be built");
            }
        }
    }

    private void init() {
        this.rndGen.shuffle(this.aggregate.getRefParticles().asList());

        this.attached.clear();

        this.detached.clear();
        this.detached.addAll(this.aggregate.getRefParticles().asList());

        Shape elementA = this.detached.poll();
        assert elementA != null;

        elementA.setCenter(0, 0, 0);

        this.attached.register(elementA);

        Shape elementB = this.detached.poll();
        assert elementB != null;

        elementB.setCenter(this.rndGen.nextDoubleOnSphere(elementA.getRadius() + elementB.getRadius()));

        this.attached.register(elementB);
    }

    private boolean buildStep() {
        resetMassCenter();
        resetDimension();

        Shape particle = detached.poll();
        assert particle != null;

        main:
        while (true) {
            particle.setCenter(this.rndGen.nextDoubleOnSphere(this.rSpawn));

            while (true) {
                this.dirBase.applyStateFrom(particle.getRefCenter());
                this.dirHead.applyStateFrom(particle.getRefCenter());

                this.movement.accept(this.attached, this.rndEng, this.dirHead);

                if (this.dirHead.getDistance(0, 0, 0) > this.rExile + particle.getRadius()) {
                    continue main;
                }

                particle.setCenter(this.dirHead);

                if (this.dirHead.getDistance(0, 0, 0) > this.rAggregate + particle.getRadius()) {
                    continue;
                }

                if (particle.overlaps(this.attached) == 0) {
                    continue;
                }

                boolean isPositioned = particle.project(this.attached, this.dir, this.step);

                if (!isPositioned) {
                    continue;
                }

                if (this.validation != null) {
                    if (!this.validation.accept(this.attached, this.rndEng, particle)) {
                        particle.setCenter(this.dirBase);

                        continue;
                    }
                }

                if (this.monitor != null) {
                    this.monitor.accept(particle, this.attached.size());
                }

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

        this.attached.forEach(e -> e.translate(-cMass.getX(), -cMass.getY(), -cMass.getZ()));
    }

    private void resetDimension() {
        this.rAggregate = this.aggregate.getRadius(this.cMass);
        this.rExile = this.rAggregate * this.fExile;
        this.rSpawn = this.rAggregate * this.fSpawn;
    }

    //--------------------------------------------------

    @Override
    public void addMonitor(BiConsumer<Shape, Integer> monitor) {

        this.monitor = monitor;
    }

    @Override
    public void setStep(double step) {

        if (step <= 0) {
            throw new IllegalArgumentException("The step must be greater than zero");
        }

        this.step = step;
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
    public void setExileFactor(double factor) {

        if (factor <= this.fSpawn) {
            throw new IllegalArgumentException("The exile factor must be greater than the spawn factor");
        }

        this.fExile = factor;
    }

    @Override
    public void setMovement(TriConsumer<FAssembly<Shape>, FRandEngine, FPoint> movement) {

        this.movement = movement;
    }

    @Override
    public void setValidation(TriFunction<FAssembly<Shape>, FRandEngine, Shape, Boolean> validation) {

        this.validation = validation;
    }
}
