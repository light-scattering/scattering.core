package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.rlca.FModelCCRLCA;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.utility.type.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelCCRLCADef implements FModelCCRLCA {
    private static final int AGGREGATE_SIZE = 6;
    private static final int FRAGMENT_SIZE = 3;
    private static final int MAX_IT_GLOBAL = 10;

    private final Dimension dimension;

    private final List<BiConsumer<FAggregate, FAggregate>> monitors;
    private final List<BiFunction<FAggregate, FAggregate, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final ScatFactory factory;
    private final FRandAspect random;

    private final FAggregate aggregate;

    private final List<FAggregate> fragments;

    private boolean symmetry;

    private FModelCCRLCADef(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

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

        this.factory = factory;
        this.random = this.factory.getRandAspect();

        this.aggregate = aggregate;

        this.fragments = new ArrayList<>();

        this.symmetry = true;
    }

    public static FModelCCRLCA create(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

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

            while (this.fragments.size() > 1) {
                buildStepVariantSymmetry();
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

        distributeFragments();
        buildFragments();

        for (FAggregate fragment : this.fragments) {
            this.monitors.forEach(e -> e.accept(null, fragment));
        }

        shuffleFragments();
    }

    private void buildStepVariantSymmetry() {

        if (this.symmetry) {
            buildStepSymmetric();
        } else {
            buildStepRandom();
        }
    }

    private void buildStepSymmetric() {

        for (int i = 0 ; i < this.fragments.size() - 1 ; i += 2) {
            FAggregate aggA = this.fragments.get(i);
            FAggregate aggB = this.fragments.get(i + 1);

            buildStepCore(aggA, aggB);
        }

        buildStepCleanup();
    }

    private void buildStepRandom() {
        FAggregate aggA;
        FAggregate aggB;

        do {
            aggA = this.random.getFRand().getElement(this.fragments, false);
            aggB = this.random.getFRand().getElement(this.fragments, false);
        } while (aggA == aggB);

        buildStepCore(aggA, aggB);

        buildStepCleanup();
    }

    private void buildStepCore(FAggregate aggA, FAggregate aggB) {

        step:
        while (true) {
            attachVariantDimension(aggA, aggB);

            for (var acceptor : this.acceptors) {
                if (!acceptor.apply(aggA, aggB)) {

                    continue step;
                }
            }

            this.monitors.forEach(e -> e.accept(aggA, aggB));

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
            factory.getFModelContext().pc().rla(this.dimension, fragment).build();
        }

        FBuffer<FBufferData> buffer = this.aggregate.getRefFExtension().getRefFBuffer();

        if (buffer != null) {
            for (FAggregate fragment : this.fragments) {
                fragment.setRefFBuffer(buffer);
            }
        }
    }

    private void shuffleFragments() {

        this.random.getFRand().shuffle(this.fragments);
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
    public void addStepMonitor(BiConsumer<FAggregate, FAggregate> monitor) {

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