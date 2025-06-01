package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.function.Function;

public class FDraftProducerDef implements FDraftProducer {

    private final ProducerCoreDef<FDraftProducer, FDraft> core;

    private final FRandGenerator random;
    private final ConstructFactory factory;

    private FDraftProducerDef(ConstructFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);
    }

    public static FDraftProducer create(ConstructFactory factory, FRandGenerator random) {

        return new FDraftProducerDef(factory, random);
    }

    @Override
    public FDraftProducer setConfig(Function<FDraft, FDraft> function) {

        return core.setConfig(function);
    }

    @Override
    public FDraftProducer addConfig(Function<FDraft, FDraft> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FDraft produce() {

        return core.getFunction().apply(factory.getFDraft());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FDraftProducer setPresetEmpty() {
        Function<FDraft, FDraft> function = (fDraft) -> fDraft;

        setConfig(function);

        return this;
    }

    @Override
    public FDraftProducer setPresetUnitX() {
        Function<FDraft, FDraft> function = (fDraft) -> {
            fDraft.getRefOrigin().getRefHead().setX(1);

            return fDraft;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FDraftProducer setPresetUnitY() {
        Function<FDraft, FDraft> function = (fDraft) -> {
            fDraft.getRefOrigin().getRefHead().setY(1);

            return fDraft;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FDraftProducer setPresetUnitZ() {
        Function<FDraft, FDraft> function = (fDraft) -> {
            fDraft.getRefOrigin().getRefHead().setZ(1);

            return fDraft;
        };

        setConfig(function);

        return this;
    }
}
