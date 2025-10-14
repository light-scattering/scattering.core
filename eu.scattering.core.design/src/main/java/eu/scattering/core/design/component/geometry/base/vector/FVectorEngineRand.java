package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.component.geometry.base.point.FPoint;

public interface FVectorEngineRand {

    FVector inSphere(FVector in);
    FVector onSphere(FVector in);

    FPoint onAxis(FPoint in, FVector axis);

    @Fragment
    FPoint ortToBaseInCircle(FPoint in, FVector dir, double radius);
    @Fragment
    FPoint ortToBaseOnCircle(FPoint in, FVector dir, double radius);
    @Fragment
    FPoint ortToHeadInCircle(FPoint in, FVector dir, double radius);
    @Fragment
    FPoint ortToHeadOnCircle(FPoint in, FVector dir, double radius);
}
