package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.rla.FModelRLA;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.engine.randomize.FRandEngine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelRLA3DDef implements FModelRLA {
    private static final int ITERATIONS = 100;
    private static final int MIN_SIZE = 5;

    private final List<BiConsumer<FAssembly<Shape>, Shape>> monitor;
    private final List<BiFunction<FAssembly<Shape>, Shape, Boolean>> validator;
    private final List<BiFunction<FAggregate, Integer, Boolean>> acceptor;

    private final FRandEngine rndEng;

    private final FAggregate aggregate;

    private final List<Shape> bases;

    private final FAssembly<Shape> attached;
    private final Queue<Shape> detached;

    private FModelRLA3DDef(FAggregate aggregate, ScatFactory factory) {

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

        this.aggregate = aggregate;

        this.bases = new ArrayList<>(this.aggregate.getRefParticles().size());

        this.attached = factory.getFAssembly();
        this.detached = new LinkedList<>(this.aggregate.getRefParticles().asList());
    }

    public static FModelRLA create(FAggregate aggregate, ScatFactory random) {

        return new FModelRLA3DDef(aggregate, random);
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
        this.rndEng.getFRand().shuffle(this.aggregate.getRefParticles().asList());

        this.bases.clear();
        this.attached.clear();

        this.detached.clear();
        this.detached.addAll(this.aggregate.getRefParticles().asList());

        Shape particle = detached.poll();
        assert particle != null;

        particle.setCenter(0, 0, 0);

        this.monitor.forEach(e -> e.accept(this.attached, particle));

        this.bases.add(particle);
        this.attached.register(particle);
    }

    private boolean buildStep() {
        Shape particle = detached.poll();

        step:
        while (this.bases.size() != 0) {
            int baseIndex = rndEng.getFRand().nextInteger(0, this.bases.size());
            Shape base = this.bases.get(baseIndex);

            boolean isPositioned = rndEng.attachLinear(particle, base, this.attached, ITERATIONS);

            if (!isPositioned) {
                this.bases.remove(base);

                continue;
            }

            for (var validator : this.validator) {
                if (!validator.apply(this.attached, particle)) {

                    continue step;
                }
            }

            this.monitor.forEach(e -> e.accept(this.attached, particle));

            this.bases.add(particle);
            this.attached.register(particle);

            return true;
        }

        return false;
    }

    //--------------------------------------------------

    @Override
    public void addStepMonitor(BiConsumer<FAssembly<Shape>, Shape> monitor) {

        this.monitor.add(monitor);
    }

    @Override
    public void addStepValidator(BiFunction<FAssembly<Shape>, Shape, Boolean> validator) {

        this.validator.add(validator);
    }

    @Override
    public void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator) {

        this.acceptor.add(validator);
    }
}
