package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.engine.randomize.FRandEngine;

import java.util.*;

public class FModelRLA3D implements FModelPC {
    private static final int ITERATIONS = 100;

    private final FRandEngine random;
    private final FAggregate aggregate;

    private final List<Shape> bases;
    private final List<Shape> attached;

    private final Queue<Shape> detached;

    private FModelRLA3D(FAggregate aggregate, FRandEngine random) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (random == null) {
            throw new IllegalArgumentException("The randomization engine is not defined");
        }

        this.random = random;
        this.aggregate = aggregate;

        this.bases = new ArrayList<>(this.aggregate.getRefParticles().size());
        this.attached = new ArrayList<>(this.aggregate.getRefParticles().size());
        this.detached = new LinkedList<>(this.aggregate.getRefParticles().asList());
    }

    public static FModelPC create(FAggregate aggregate, FRandEngine random) {

        return new FModelRLA3D(aggregate, random);
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

        this.bases.clear();

        this.detached.clear();
        this.detached.addAll(this.aggregate.getRefParticles().asList());

        Shape element = detached.poll();

        if (element == null) {
            throw new IllegalStateException("The aggregate must consist of at least one particle");
        }

        element.setCenter(0, 0, 0);

        this.bases.add(element);
        this.attached.add(element);
    }

    private boolean buildStep() {
        Shape particle = detached.poll();

        while (this.bases.size() != 0) {
            int baseIndex = random.getFRand().nextInteger(0, this.bases.size());
            Shape base = this.bases.get(baseIndex);

            boolean isPositioned = random.attachLinear(particle, base, this.attached, ITERATIONS);

            if (!isPositioned) {
                this.bases.remove(base);

                continue;
            }

            this.bases.add(particle);
            this.attached.add(particle);

            return true;
        }

        return false;
    }
}
