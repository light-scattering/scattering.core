package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.extension.Producer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FSegmentProducer extends Producer<FSegment> {

    @Override
    FSegment produce();
    @Override
    List<FSegment> getList();
    @Override
    List<FSegment> getListRandomized(int quantity);
    @Override
    List<FSegment> getListFixed(int quantity);
    @Override
    Stream<FSegment> stream();

    // -------------------------------------------------------------------------------------------------

    FSegmentProducer withCustomRule(Function<FSegmentFactory, FSegment> function, int weight);
    FSegmentProducer withCustomRule(BiFunction<FSegmentFactory, FRandAspect, FSegment> function, int weight);

    FSegmentProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FSegmentProducer withCustomRule(Function<FSegmentFactory, FSegment> function) {

        return withCustomRule(function, 1);
    }

    default FSegmentProducer withCustomRule(BiFunction<FSegmentFactory, FRandAspect, FSegment> function) {

        return withCustomRule(function, 1);
    }

    default FSegmentProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
