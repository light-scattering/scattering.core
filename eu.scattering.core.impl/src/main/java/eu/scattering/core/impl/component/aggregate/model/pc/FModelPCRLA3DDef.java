package eu.scattering.core.impl.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.rla.FModelPCRLA;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.aspect.randomize.FRandAspect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelPCRLA3DDef implements FModelPCRLA {
    private static final int AGGREGATE_SIZE = 3;
    private static final int ITERATIONS = 100;

    private final List<BiConsumer<FAggregate, Shape>> monitors;
    private final List<BiFunction<FAggregate, Shape, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final FRandAspect rndEng;

    private final FAggregate aggregate;

    private final List<Shape> bases;

    private final FAssembly<Shape> attached;
    private final Queue<Shape> detached;

    private FModelPCRLA3DDef(FAggregate aggregate, ScatFactory factory) {

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

        this.aggregate = aggregate;

        this.bases = new ArrayList<>(this.aggregate.getRefParticles().size());

        this.attached = this.aggregate.getRefParticles();
        this.detached = new LinkedList<>();
    }

    public static FModelPCRLA create(FAggregate aggregate, ScatFactory random) {

        return new FModelPCRLA3DDef(aggregate, random);
    }

    @Override
    public void build() {

        if (this.aggregate.getRefParticles().size() < AGGREGATE_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + AGGREGATE_SIZE + " particles");
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

            this.monitors.forEach(e -> e.accept(this.aggregate, null));

            for (var validator : this.validators) {
                if (validator.apply(this.aggregate, iteration)) {
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

        this.detached.clear();
        this.detached.addAll(this.attached.asList());

        this.attached.clear();

        Shape particle = detached.poll();
        assert particle != null;

        particle.setCenter(0, 0, 0);

        this.monitors.forEach(e -> e.accept(this.aggregate, particle));

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

            for (var acceptor : this.acceptors) {
                if (!acceptor.apply(this.aggregate, particle)) {

                    continue step;
                }
            }

            this.monitors.forEach(e -> e.accept(this.aggregate, particle));

            this.bases.add(particle);
            this.attached.register(particle);

            return true;
        }

        return false;
    }

    //--------------------------------------------------

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
