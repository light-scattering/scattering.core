package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentFactory;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class FSegmentProducerDef implements FSegmentProducer {

    private final FSegmentFactory factory;
    private final ProducerCoreDef<FSegment> processor;

    private FSegmentProducerDef(FSegmentFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.processor = new ProducerCoreDef<>(randomizer);
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
    public List<FSegment> getListAuto() {

        return this.processor.getListAdopted(null);
    }

    @Override
    public List<FSegment> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity, null);
    }

    @Override
    public List<FSegment> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity, null);
    }
}
