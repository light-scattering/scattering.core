package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.component.geometry.base.point.FPoint;

public interface FRayEngineRand {

    FPoint ortToBaseInCircle(FPoint in, FRay dir, double radius);
    FPoint ortToBaseOnCircle(FPoint in, FRay dir, double radius);
}
