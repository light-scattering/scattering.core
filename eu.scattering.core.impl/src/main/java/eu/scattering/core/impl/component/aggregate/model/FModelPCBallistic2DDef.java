package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallistic;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.engine.randomize.FRandEngine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelPCBallistic2DDef implements FModelPCBallistic {
    private static final int ITERATIONS = 100;
    private static final int MIN_SIZE = 5;

    private final List<BiConsumer<FAggregate, Shape>> monitor;
    private final List<BiFunction<FAggregate, Integer, Boolean>> acceptor;
    private final List<BiFunction<FAggregate, Shape, Boolean>> validator;

    private final FRandEngine rndEng;

    private final FAggregate aggregate;

    private final FSphere range;

    private final FAssembly<Shape> attached;
    private final Queue<Shape> detached;

    private FModelPCBallistic2DDef(FAggregate aggregate, ScatFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.monitor = new ArrayList<>();
        this.acceptor = new ArrayList<>();
        this.validator = new ArrayList<>();

        this.rndEng = factory.getFRandEngine();

        this.aggregate = aggregate;

        this.range = factory.getFSphere();

        this.attached = this.aggregate.getRefParticles();
        this.detached = new LinkedList<>();
    }

    public static FModelPCBallistic create(FAggregate aggregate, ScatFactory factory) {

        return new FModelPCBallistic2DDef(aggregate, factory);
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
        this.rndEng.getFRand().shuffle(this.aggregate.getRefParticles().asList());

        this.detached.clear();
        this.detached.addAll(this.attached.asList());

        this.attached.clear();

        Shape particle = detached.poll();
        assert particle != null;

        particle.setCenter(0, 0, 0);

        this.monitor.forEach(e -> e.accept(this.aggregate, particle));

        this.attached.register(particle);
    }

    private boolean buildStep() {
        Shape particle = detached.poll();

        step:
        while (true) {
            int targetIndex = rndEng.getFRand().nextInteger(0, this.attached.size());
            Shape target = this.attached.asList().get(targetIndex);

            this.range.setCenter(target.getRefCenter());
            this.range.setRadius(this.aggregate.getRadius(target.getRefCenter()));

            boolean isPositioned = rndEng.project2D(particle, this.range, this.attached, ITERATIONS);

            if (!isPositioned) {
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
}
