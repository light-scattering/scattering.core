package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftFactory;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftProducer;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FDraftProducerDef implements FDraftProducer {

    private final FDraftFactory factory;
    private final ProducerCoreDef<FDraft> processor;
    private final FRandEngine rndEngine;

    private FDraftProducerDef(FDraftFactory factory, FRandEngine randomizer) {

        this.factory = factory;
        this.rndEngine = randomizer;
        this.processor = new ProducerCoreDef<>(this.rndEngine.getFRand());
    }

    public static FDraftProducer create(FDraftFactory factory, FRandEngine randomizer) {

        return new FDraftProducerDef(factory, randomizer);
    }

    @Override
    public FDraftProducer withCustomRule(Function<FDraftFactory, FDraft> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FDraftProducer withCustomRule(BiFunction<FDraftFactory, FRandEngine, FDraft> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, rndEngine), weight);

        return this;
    }



    // -------------------------------------------------------------------------------------------------

    @Override
    public FDraftProducer withFVector(FVectorProducer origin, int weight) {
        Function<FDraftFactory, FDraft> function = (factory) ->
                factory.getRefFDraft(origin.produce());

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FDraft produce() {

        return processor.produce();
    }

    @Override
    public List<FDraft> getList() {

        return this.processor.getList();
    }

    @Override
    public List<FDraft> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<FDraft> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public Stream<FDraft> stream() {

        return this.processor.stream();
    }
}
