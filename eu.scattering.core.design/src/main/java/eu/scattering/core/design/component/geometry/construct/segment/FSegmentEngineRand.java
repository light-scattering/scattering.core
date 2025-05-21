package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.base.point.FPoint;

public interface FSegmentEngineRand {

    // Reset the current state (the value of the input element is meaningless).

    FPoint rndPosOnSegment(FPoint in, FSegment dir);

    FPoint rndPosBaseInCircle(FPoint in, FSegment dir, double radius);
    FPoint rndPosBaseOnCircle(FPoint in, FSegment dir, double radius);

    FPoint rndPosHeadInCircle(FPoint in, FSegment dir, double radius);
    FPoint rndPosHeadOnCircle(FPoint in, FSegment dir, double radius);
}
