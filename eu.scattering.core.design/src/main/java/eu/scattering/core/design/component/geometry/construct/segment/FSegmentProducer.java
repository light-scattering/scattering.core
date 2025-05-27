package eu.scattering.core.design.component.geometry.construct.segment;

import java.util.function.Function;

public interface FSegmentProducer {

    FSegmentProducer setConfig(Function<FSegment, FSegment> function);
    FSegmentProducer addConfig(Function<FSegment, FSegment> function, double probability);

    FSegment produce();

    // -------------------------------------------------------------------------------------------------

    FSegmentProducer setPresetEmpty();

    FSegmentProducer setPresetUnitX();
    FSegmentProducer setPresetUnitY();
    FSegmentProducer setPresetUnitZ();
}
