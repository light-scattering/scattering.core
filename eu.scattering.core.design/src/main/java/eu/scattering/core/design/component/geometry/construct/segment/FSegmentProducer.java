package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public interface FSegmentProducer {

    FSegmentProducer setConfig(Function<FSegment, FSegment> function, double probability);
    FSegmentProducer addConfig(Function<FSegment, FSegment> function, double probability);

    FSegment produce();

    // -------------------------------------------------------------------------------------------------

    FSegmentProducer setPresetUnitOX();
    FSegmentProducer setPresetUnitOY();
    FSegmentProducer setPresetUnitOZ();

    FSegmentProducer setPresetFixedPoint(FPos3D point, double length);

    // -------------------------------------------------------------------------------------------------

    default FSegmentProducer setConfig(Function<FSegment, FSegment> function) {

        return setConfig(function, 1);
    }
}
