package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FSegmentProducer{

    FSegment produce();

    Stream<FSegment> stream();

    List<FSegment> getListAuto();
    List<FSegment> getListRandomized(int quantity);
    List<FSegment> getListFixed(int quantity);

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
