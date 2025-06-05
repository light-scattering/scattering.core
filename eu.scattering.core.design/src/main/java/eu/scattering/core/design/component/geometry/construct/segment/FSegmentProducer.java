package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FSegmentProducer extends Iterable<FSegment> {

    FSegment produce();
    Stream<FSegment> stream();

    // -------------------------------------------------------------------------------------------------

    FSegmentProducer withCustomRule(Function<FSegmentFactory, FSegment> function, int weight);

    FSegmentProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FSegmentProducer withCustomRule(Function<FSegmentFactory, FSegment> function) {

        return withCustomRule(function, 1);
    }

    default FSegmentProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
