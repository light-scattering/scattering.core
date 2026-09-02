package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftFactory;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftProducer;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FDraftProducerDef implements FDraftProducer {

    private final FDraftFactory factory;
    private final ProducerCoreDef<FDraft> processor;
    private final FRandAspect rndAspect;

    private FDraftProducerDef(FDraftFactory factory, FRandAspect randomizer) {

        this.factory = factory;
        this.rndAspect = randomizer;
        this.processor = new ProducerCoreDef<>(this.rndAspect.generator());
    }

    public static FDraftProducer create(FDraftFactory factory, FRandAspect randomizer) {

        return new FDraftProducerDef(factory, randomizer);
    }

    @Override
    public FDraftProducer withCustomRule(Function<FDraftFactory, FDraft> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FDraftProducer withCustomRule(BiFunction<FDraftFactory, FRandAspect, FDraft> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, rndAspect), weight);

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
    public FDraftProducer setRetriesLimited(int limit) {

        this.processor.setRetriesLimited(limit);

        return this;
    }

    @Override
    public FDraftProducer setRetriesInfinite() {

        this.processor.setRetriesInfinite();

        return this;
    }

    @Override
    public FDraftProducer setSkipOnFailure(boolean skip) {

        this.processor.setSkipOnFailure(skip);

        return this;
    }

    @Override
    public Stream<FDraft> stream() {

        return this.processor.stream();
    }
}
