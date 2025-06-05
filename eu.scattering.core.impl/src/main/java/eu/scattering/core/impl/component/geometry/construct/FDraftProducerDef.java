package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftFactory;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class FDraftProducerDef implements FDraftProducer {

    private final FDraftFactory factory;
    private final ProducerCoreDef<FDraft> processor;

    private FDraftProducerDef(FDraftFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.processor = new ProducerCoreDef<>(randomizer);
    }

    public static FDraftProducer create(FDraftFactory factory, FRandGenerator randomizer) {

        return new FDraftProducerDef(factory, randomizer);
    }

    @Override
    public FDraftProducer withCustomRule(Function<FDraftFactory, FDraft> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FDraft produce() {

        return processor.produce();
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
    public Stream<FDraft> stream() {

        return this.processor.stream();
    }

    @Override
    public Iterator<FDraft> iterator() {

        return this.processor.getIterator();
    }
}
