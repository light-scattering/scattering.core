package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.base.point.FPoint;

public interface FSegmentEngineRand {

    FPoint onSegment(FPoint in, FSegment ref);

    FPoint ortToPosAInCircle(FPoint in, FSegment ref, double radius);
    FPoint ortToPosAOnCircle(FPoint in, FSegment ref, double radius);

    FPoint ortToPosBInCircle(FPoint in, FSegment ref, double radius);
    FPoint ortToPosBOnCircle(FPoint in, FSegment ref, double radius);
}
