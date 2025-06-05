package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentFactory;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreAdvancedDef;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class FSegmentProducerDef implements FSegmentProducer {

    private final FSegmentFactory factory;
    private final ProducerCoreAdvancedDef<FSegment> processor;

    private FSegmentProducerDef(FSegmentFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.processor = new ProducerCoreAdvancedDef<>(randomizer);
    }

    public static FSegmentProducer create(FSegmentFactory factory, FRandGenerator randomizer) {

        return new FSegmentProducerDef(factory, randomizer);
    }

    @Override
    public FSegmentProducer withCustomRule(Function<FSegmentFactory, FSegment> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FSegment produce() {

        return processor.produce();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSegmentProducer withFVector(FVectorProducer origin, int weight) {
        Function<FSegmentFactory, FSegment> function = (factory) ->
                factory.getRefFSegment(origin.produce());

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Stream<FSegment> stream() {

        return this.processor.stream();
    }

    @Override
    public Iterator<FSegment> iterator() {

        return this.processor.getIterator();
    }
}
