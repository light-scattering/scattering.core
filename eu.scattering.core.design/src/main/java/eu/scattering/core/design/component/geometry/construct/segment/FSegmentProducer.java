package eu.scattering.core.design.component.geometry.construct.segment;

import java.util.function.Function;

public interface FSegmentProducer {

    void setConfig(Function<FSegment, FSegment> function);
    FSegmentProducer addConfig(Function<FSegment, FSegment> function, double probability);

    FSegment produce();

    // -------------------------------------------------------------------------------------------------

    void setPresetUnitOX();
    FSegmentProducer addPresetUnitOX(double probability);

    void setPresetUnitOY();
    FSegmentProducer addPresetUnitOY(double probability);

    void setPresetUnitOZ();
    FSegmentProducer addPresetUnitOZ(double probability);
}
