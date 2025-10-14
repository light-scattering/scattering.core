package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallistic;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.lambda.TriFunction;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiConsumer;

public class FModelPCBallistic3DDef implements FModelPCBallistic {
    private static final int ITERATIONS = 100;
    private static final int MIN_SIZE = 5;

    private BiConsumer<Shape, Integer> monitor;
    private TriFunction<FAssembly<Shape>, FRandEngine, Shape, Boolean> validator;

    private final FRandEngine rndEng;

    private final FAggregate aggregate;

    private final FSphere range;

    private final FAssembly<Shape> attached;
    private final Queue<Shape> detached;

    private FModelPCBallistic3DDef(FAggregate aggregate, ScatFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.rndEng = factory.getFRandEngine();

        this.aggregate = aggregate;

        this.range = factory.getFSphere();

        this.attached = factory.getFAssembly();
        this.detached = new LinkedList<>(this.aggregate.getRefParticles().asList());
    }

    public static FModelPCBallistic create(FAggregate aggregate, ScatFactory factory) {

        return new FModelPCBallistic3DDef(aggregate, factory);
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
        this.rndEng.getFRand().shuffle(this.aggregate.getRefParticles().asList());

        this.attached.clear();

        this.detached.clear();
        this.detached.addAll(this.aggregate.getRefParticles().asList());

        Shape particle = detached.poll();
        assert particle != null;

        particle.setCenter(0, 0, 0);

        if (this.monitor != null) {
            this.monitor.accept(particle, this.attached.size());
        }

        this.attached.register(particle);
    }

    private boolean buildStep() {
        Shape particle = detached.poll();

        while (true) {
            int targetIndex = rndEng.getFRand().nextInteger(0, this.attached.size());
            Shape target = this.attached.asList().get(targetIndex);

            this.range.setCenter(target.getRefCenter());
            this.range.setRadius(this.aggregate.getRadius(target.getRefCenter()));

            boolean isPositioned = rndEng.project(particle, this.range, this.attached, ITERATIONS);

            if (!isPositioned) {
                continue;
            }

            if (this.validator != null) {
                if (!this.validator.accept(this.attached, this.rndEng, particle)) {

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

    //--------------------------------------------------

    @Override
    public void setMonitor(BiConsumer<Shape, Integer> monitor) {

        this.monitor = monitor;
    }

    @Override
    public void setValidator(TriFunction<FAssembly<Shape>, FRandEngine, Shape, Boolean> validation) {

        this.validator = validation;
    }
}
