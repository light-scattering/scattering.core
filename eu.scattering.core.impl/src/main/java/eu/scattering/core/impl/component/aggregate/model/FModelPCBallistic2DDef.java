package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallistic;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.engine.randomize.FRandEngine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;

public class FModelPCBallistic2DDef implements FModelPCBallistic {
    private static final int ITERATIONS = 100;
    private static final int MIN_SIZE = 5;

    private BiConsumer<Shape, Integer> monitor;

    private final FRandEngine random;
    private final FAggregate aggregate;

    private final FSphere range;

    private final List<Shape> attached;
    private final Queue<Shape> detached;

    private FModelPCBallistic2DDef(FAggregate aggregate, ScatFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.random = factory.getFRandEngine();
        this.aggregate = aggregate;

        this.range = factory.getFSphere();

        this.attached = new ArrayList<>(this.aggregate.getRefParticles().size());
        this.detached = new LinkedList<>(this.aggregate.getRefParticles().asList());
    }

    public static FModelPCBallistic create(FAggregate aggregate, ScatFactory factory) {

        return new FModelPCBallistic2DDef(aggregate, factory);
    }

    @Override
    public void build() {

        if (this.aggregate.getRefParticles().size() < 5) {
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
        this.random.getFRand().shuffle(this.aggregate.getRefParticles().asList());

        this.detached.clear();
        this.detached.addAll(this.aggregate.getRefParticles().asList());

        this.detached.forEach(e -> e.setCenterZ(0));

        Shape element = detached.poll();
        assert element != null;

        element.setCenter(0, 0, 0);

        this.attached.add(element);
    }

    private boolean buildStep() {
        Shape particle = detached.poll();

        while (true) {
            int targetIndex = random.getFRand().nextInteger(0, this.attached.size());
            Shape target = this.attached.get(targetIndex);

            this.range.setCenter(target.getRefCenter());
            this.range.setRadius(this.aggregate.getRadius(target.getRefCenter()));

            boolean isPositioned = random.project2D(particle, this.range, this.attached, ITERATIONS);

            if (!isPositioned) {
                continue;
            }

            if (this.monitor != null) {
                this.monitor.accept(particle, this.attached.size());
            }

            this.attached.add(particle);

            return true;
        }
    }

    //--------------------------------------------------

    @Override
    public void addMonitor(BiConsumer<Shape, Integer> monitor) {

        this.monitor = monitor;
    }
}
