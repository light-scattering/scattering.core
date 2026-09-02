package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.rlca.FModelCCRLCA;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.utility.lambda.TriConsumer;
import eu.scattering.core.design.utility.type.option.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FModelCCRLCADef implements FModelCCRLCA {
    private static final int AGGREGATE_SIZE = 6;
    private static final int FRAGMENT_SIZE = 3;
    private static final int MAX_IT_GLOBAL = 10;

    private final Dimension dimension;

    private final List<Consumer<FAggregate>> viewers;
    private final List<TriConsumer<FAggregate, FAggregate, Integer>> monitors;
    private final List<BiFunction<FAggregate, FAggregate, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final ScatterFactory factory;
    private final FRandAspect random;

    private final FAggregate aggregate;

    private final List<FAggregate> fragments;

    private boolean symmetry;

    private FModelCCRLCADef(Dimension dimension, FAggregate aggregate, ScatterFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.dimension = dimension;

        this.viewers = new ArrayList<>();
        this.monitors = new ArrayList<>();
        this.acceptors = new ArrayList<>();
        this.validators = new ArrayList<>();

        this.factory = factory;
        this.random = this.factory.random();

        this.aggregate = aggregate;

        this.fragments = new ArrayList<>();

        this.symmetry = true;
    }

    public static FModelCCRLCA create(Dimension dimension, FAggregate aggregate, ScatterFactory factory) {

        return new FModelCCRLCADef(dimension, aggregate, factory);
    }

    @Override
    public void build() {

        if (this.aggregate.getRefParticles().size() < AGGREGATE_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + AGGREGATE_SIZE + " particles");
        }

        int iteration = 0;
        int validation = 0;

        generation:
        while (iteration ++ < MAX_IT_GLOBAL) {

            init();

            AtomicInteger index = new AtomicInteger(0);
            while (this.fragments.size() > 1) {
                buildStepVariantSymmetry(index);
            }

            for (var monitor : this.monitors) {
                monitor.accept(this.aggregate, null, index.get());
            }

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

        distributeFragments();
        buildFragments();

        for (FAggregate fragment : this.fragments) {
            this.viewers.forEach(e -> e.accept(fragment));
        }

        shuffleFragments();
    }

    private void buildStepVariantSymmetry(AtomicInteger index) {

        if (this.symmetry) {
            buildStepSymmetric(index);
        } else {
            buildStepRandom(index);
        }
    }

    private void buildStepSymmetric(AtomicInteger index) {

        for (int i = 0 ; i < this.fragments.size() - 1 ; i += 2) {
            FAggregate aggA = this.fragments.get(i);
            FAggregate aggB = this.fragments.get(i + 1);

            buildStepCore(aggA, aggB, index);
        }

        buildStepCleanup();
    }

    private void buildStepRandom(AtomicInteger index) {
        FAggregate aggA;
        FAggregate aggB;

        do {
            aggA = this.random.generator().getElement(this.fragments, false);
            aggB = this.random.generator().getElement(this.fragments, false);
        } while (aggA == aggB);

        buildStepCore(aggA, aggB, index);

        buildStepCleanup();
    }

    private void buildStepCore(FAggregate aggA, FAggregate aggB, AtomicInteger index) {

        step:
        while (true) {
            attachVariantDimension(aggA, aggB);

            for (var acceptor : this.acceptors) {
                if (!acceptor.apply(aggA, aggB)) {

                    continue step;
                }
            }

            for (var monitor : this.monitors) {
                monitor.accept(aggA, aggB, index.get());
            }

            index.set(index.get() + 1);

            aggA.merge(aggB, true);

            break;
        }
    }

    private void buildStepCleanup() {

        removeFragments();
        shuffleFragments();
    }

    private void attachVariantDimension(FAggregate aggA, FAggregate aggB) {

        switch (this.dimension) {
            case D3 -> this.random.attach(aggA, aggB);
            case D2 -> this.random.attachOnSurface(aggA, aggB);
        }
    }

    private void distributeFragments() {

        this.fragments.clear();

        for (int i = 0; i < this.aggregate.size() / FRAGMENT_SIZE; i++) {
            this.fragments.add(this.factory.getFAggregate());
        }

        for (int i = 0 ; i < this.aggregate.size() ; i++) {
            this.fragments.get(i % this.fragments.size()).addRefParticle(this.aggregate.getRefParticles().asList().get(i));
        }
    }

    private void buildFragments() {

        for (FAggregate fragment : this.fragments) {
            factory.models().pc().rla(this.dimension, fragment).build();
        }

        FBuffer<FBufferData> buffer = this.aggregate.getRefFExtension().getRefFBuffer();

        if (buffer != null) {
            for (FAggregate fragment : this.fragments) {
                fragment.setRefFBuffer(buffer);
            }
        }
    }

    private void shuffleFragments() {

        this.random.generator().shuffle(this.fragments);
    }

    private void removeFragments() {
        List<FAggregate> elements = this.fragments.stream().filter((fragment) -> fragment.size() > 0).toList();

        this.fragments.clear();
        this.fragments.addAll(elements);
    }

    //--------------------------------------------------

    @Override
    public boolean getSymmetry() {

        return this.symmetry;
    }

    @Override
    public void setSymmetry(boolean symmetry) {

        this.symmetry = symmetry;
    }

    @Override
    public void addFragmentViewer(Consumer<FAggregate> viewer) {

        this.viewers.add(viewer);
    }

    @Override
    public void addStepMonitor(TriConsumer<FAggregate, FAggregate, Integer> monitor) {

        this.monitors.add(monitor);
    }

    @Override
    public void addStepAcceptor(BiFunction<FAggregate, FAggregate, Boolean> acceptor) {

        this.acceptors.add(acceptor);
    }

    @Override
    public void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator) {

        this.validators.add(validator);
    }
}