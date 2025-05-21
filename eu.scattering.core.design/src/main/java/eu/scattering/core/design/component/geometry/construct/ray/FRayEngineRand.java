package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.component.geometry.base.point.FPoint;

public interface FRayEngineRand {

    // Reset the current state (the value of the input element is meaningless).

    FPoint rndPosBaseInCircle(FPoint in, FRay dir, double radius);
    FPoint rndPosBaseOnCircle(FPoint in, FRay dir, double radius);
}
