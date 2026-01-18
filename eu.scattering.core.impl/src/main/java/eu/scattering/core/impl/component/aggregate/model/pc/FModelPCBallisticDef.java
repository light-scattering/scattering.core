package eu.scattering.core.impl.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallistic;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.type.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelPCBallisticDef implements FModelPCBallistic {
    private static final int MAX_IT_CORRECTIONS = 100;
    private static final int MAX_IT_GLOBAL = 10;
    private static final int MIN_SIZE = 3;

    private final Dimension dimension;

    private final List<BiConsumer<FAggregate, Shape>> monitors;
    private final List<BiFunction<FAggregate, Shape, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final FRandAspect random;

    private final FAggregate aggregate;

    private final FSphere range;

    private final FAssembly<Shape> attached;
    private final List<Shape> detached;

    private FModelPCBallisticDef(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

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

        this.range = factory.getFSphere();

        this.attached = this.aggregate.getRefParticles();
        this.detached = new ArrayList<>(this.aggregate.size());
    }

    public static FModelPCBallistic create(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

        return new FModelPCBallisticDef(dimension, aggregate, factory);
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

        this.detached.clear();
        this.detached.addAll(this.attached.asList());

        this.attached.clear();

        initParticleA();
    }

    private void initParticleA() {
        Shape particle = this.random.getFRand().getElement(this.detached, true);

        particle.setCenter(0, 0, 0);

        this.monitors.forEach(e -> e.accept(null, particle));
        this.attached.register(particle);
    }

    private boolean buildStep() {
        Shape particle = this.random.getFRand().getElement(this.detached, false);

        step:
        while (true) {
            int targetIndex = random.getFRand().nextInteger(0, this.attached.size());
            Shape target = this.attached.asList().get(targetIndex);

            this.range.setCenter(target.getRefCenter());
            this.range.setRadius(this.aggregate.getRadius(target.getRefCenter()));

            double distance = projectVariantDimension(particle);

            if (distance < 0) {
                continue;
            }

            for (var acceptor : this.acceptors) {
                if (!acceptor.apply(this.aggregate, particle)) {

                    continue step;
                }
            }

            this.monitors.forEach(e -> e.accept(this.aggregate, particle));
            this.attached.register(particle);
            this.detached.remove(particle);

            return true;
        }
    }

    private double projectVariantDimension(Shape particle) {

        return switch (this.dimension) {
            case D3 -> random.project(particle, this.range, this.attached, MAX_IT_CORRECTIONS);
            case D2 -> random.project2D(particle, this.range, this.attached, MAX_IT_CORRECTIONS);
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
