package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.engine.randomize.FRandEngine;

import java.util.*;

public class FModelRLA implements FModelPC {
    private static final int ITERATIONS = 100;

    private final FRandEngine random;
    private final FAggregate aggregate;

    private List<Shape> positioned;
    private List<Shape> available;

    private Queue<Shape> remaining;

    private FModelRLA(FAggregate aggregate, FRandEngine random) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (random == null) {
            throw new IllegalArgumentException("The randomization engine is not defined");
        }

        this.random = random;
        this.aggregate = aggregate;

        this.positioned = new ArrayList<>(this.aggregate.getRefParticles().size());
        this.available = new ArrayList<>(this.aggregate.getRefParticles().size());
        this.remaining  = new LinkedList<>(this.aggregate.getRefParticles().asList());
    }

    public static FModelPC create(FAggregate aggregate, FRandEngine random) {

        return new FModelRLA(aggregate, random);
    }

    @Override
    public void init() {
        this.random.getFRand().shuffle(this.aggregate.getRefParticles().asList());

        this.positioned.clear();
        this.available.clear();

        this.remaining.clear();
        this.remaining.addAll(this.aggregate.getRefParticles().asList());

        Shape element = remaining.poll();

        if (element == null) {
            throw new IllegalStateException("The aggregate must consist of at least one particle");
        }

        element.setCenter(0, 0, 0);

        this.positioned.add(element);
        this.available.add(element);
    }

    @Override
    public void build() {


    }

    @Override
    public boolean buildStep(List<Shape> aggregated, Queue<Shape> remaining) {


        return false;



    }

    private boolean buildStep() {
        Shape candidate = remaining.poll();

        while (this.available.size() != 0) {
            int baseIndex = random.getFRand().nextInteger(0, this.available.size());
            Shape base = this.available.get(baseIndex);

            boolean isPositioned = random.attachLinear(candidate, base, this.remaining, ITERATIONS);

            if (isPositioned) {
                this.positioned.add(candidate);

            }

        }



        return false;
    }
}
