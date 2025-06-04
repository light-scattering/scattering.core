package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreBasicDef;

import java.util.function.Function;

public class FDraftProducerDef implements FDraftProducer {

    private final ProducerCoreBasicDef<FDraftProducer, FDraft> core;

    private final FRandGenerator random;
    private final ConstructFactory factory;

    private FDraftProducerDef(ConstructFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreBasicDef<>(this, this.random);
    }

    public static FDraftProducer create(ConstructFactory factory, FRandGenerator random) {

        return new FDraftProducerDef(factory, random);
    }

    @Override
    public void setConfig(Function<FDraft, FDraft> function) {

        core.setConfig(function, 1);
    }

    @Override
    public FDraftProducer addConfig(Function<FDraft, FDraft> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FDraft produce() {

        if (core.getSize() == 0) {
            throw new IllegalStateException("The producer is not configured");
        }

        return core.getFunction().apply(factory.getFDraft());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public void setPresetUnitX() {
        Function<FDraft, FDraft> function = (fDraft) -> {
            fDraft.getRefOrigin().getRefHead().setX(1);

            return fDraft;
        };

        setConfig(function);
    }

    @Override
    public FDraftProducer addPresetUnitX(double probability) {
        Function<FDraft, FDraft> function = (fDraft) -> {
            fDraft.getRefOrigin().getRefHead().setX(1);

            return fDraft;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetUnitY() {
        Function<FDraft, FDraft> function = (fDraft) -> {
            fDraft.getRefOrigin().getRefHead().setY(1);

            return fDraft;
        };

        setConfig(function);
    }

    @Override
    public FDraftProducer addPresetUnitY(double probability) {
        Function<FDraft, FDraft> function = (fDraft) -> {
            fDraft.getRefOrigin().getRefHead().setY(1);

            return fDraft;
        };

        addConfig(function, 1);

        return this;
    }

    @Override
    public void setPresetUnitZ() {
        Function<FDraft, FDraft> function = (fDraft) -> {
            fDraft.getRefOrigin().getRefHead().setZ(1);

            return fDraft;
        };

        setConfig(function);
    }

    @Override
    public FDraftProducer addPresetUnitZ(double probability) {
        Function<FDraft, FDraft> function = (fDraft) -> {
            fDraft.getRefOrigin().getRefHead().setZ(1);

            return fDraft;
        };

        addConfig(function, 1);

        return this;
    }
}
