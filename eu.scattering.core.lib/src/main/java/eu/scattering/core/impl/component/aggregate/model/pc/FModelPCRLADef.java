package eu.scattering.core.impl.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.rla.FModelPCRLA;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.utility.type.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelPCRLADef implements FModelPCRLA {
    private static final int MAX_IT_CORRECTIONS = 100;
    private static final int MAX_IT_GLOBAL = 10;
    private static final int MIN_SIZE = 3;

    private final Dimension dimension;

    private final List<BiConsumer<FAggregate, Shape>> monitors;
    private final List<BiFunction<FAggregate, Shape, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final FRandAspect random;

    private final FAggregate aggregate;

    private final List<Shape> bases;

    private final FAssembly<Shape> attached;
    private final List<Shape> detached;

    private FModelPCRLADef(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.dimension = dimension;

        this.monitors = new ArrayList<>();
        this.acceptors = new ArrayList<>();
        this.validators = new ArrayList<>();

        this.random = factory.getRandAspect();

        this.aggregate = aggregate;

        this.bases = new ArrayList<>(this.aggregate.getRefParticles().size());

        this.attached = this.aggregate.getRefParticles();
        this.detached = new ArrayList<>(this.aggregate.size());
    }

    public static FModelPCRLA create(Dimension dimension, FAggregate aggregate, ScatFactory random) {

        return new FModelPCRLADef(dimension, aggregate, random);
    }

    @Override
    public void build() {

        if (this.aggregate.getRefParticles().size() < MIN_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + MIN_SIZE + " particles");
        }

        int iteration = 0;
        int validation = 0;

        generation:
        while (iteration ++ < MAX_IT_GLOBAL) {

            init();

            while (this.detached.size() != 0) {
                if (!buildStep()) {
                    continue generation;
                }
            }

            this.monitors.forEach(e -> e.accept(this.aggregate, null));

            for (var validator : this.validators) {
                if (validator.apply(this.aggregate, validation)) {
                    continue;
                }

                validation++;

                continue generation;
            }

            return;
        }

        throw new RuntimeException("The aggregate could not be built");
    }

    private void init() {
        this.attached.register(this.detached);

        this.bases.clear();

        this.detached.clear();
        this.detached.addAll(this.attached.asList());

        this.attached.clear();

        initParticleA();
    }

    private void initParticleA() {
        Shape particle = this.random.getFRand().getElement(this.detached, true);

        particle.setCenter(0, 0, 0);

        this.monitors.forEach(e -> e.accept(null, particle));

        this.bases.add(particle);

        this.attached.register(particle);
    }

    private boolean buildStep() {
        Shape particle = this.random.getFRand().getElement(this.detached, false);

        step:
        while (this.bases.size() != 0) {
            int baseIndex = random.getFRand().nextInteger(0, this.bases.size());
            Shape base = this.bases.get(baseIndex);

            boolean isPositioned = attachVariantDimension(particle, base);

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
            this.detached.remove(particle);

            return true;
        }

        return false;
    }

    private boolean attachVariantDimension(Shape particle, Shape base) {

        return switch (this.dimension) {
            case D3 -> random.attachLinear(particle, base, this.attached, MAX_IT_CORRECTIONS);
            case D2 -> random.attachLinear2D(particle, base, this.attached, MAX_IT_CORRECTIONS);
        };
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
