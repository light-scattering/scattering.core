package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.engine.randomize.FRandEngine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FModelBallistic2DDef implements FModelPC {
    private static final int ITERATIONS = 100;

    private final FRandEngine random;
    private final FAggregate aggregate;

    private final FSphere range;

    private final List<Shape> attached;
    private final Queue<Shape> detached;

    private FModelBallistic2DDef(FAggregate aggregate, ScatFactory factory) {

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

    public static FModelPC create(FAggregate aggregate, ScatFactory factory) {

        return new FModelBallistic2DDef(aggregate, factory);
    }

    @Override
    public void build() {
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

        if (element == null) {
            throw new IllegalStateException("The aggregate must consist of at least one particle");
        }

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

            this.attached.add(particle);

            return true;
        }
    }
}
