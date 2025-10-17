package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;

public interface FPointEngineRand {

    FPoint inRange(FPoint in, FPairPos3D range);

    FPoint inSphere(FPoint in);
    FPoint inSphere(FPoint in, double radius);

    FPoint onSphere(FPoint in);
    FPoint onSphere(FPoint in, double radius);

    FPoint onAxis(FPoint in);
    FPoint onAxis(FPoint in, FPoint axis);

    @Fragment
    FPoint ortToBaseInCircle(FPoint in, FPoint dir, double radius);
    @Fragment
    FPoint ortToBaseOnCircle(FPoint in, FPoint dir, double radius);
    @Fragment
    FPoint ortToHeadInCircle(FPoint in, FPoint dir, double radius);
    @Fragment
    FPoint ortToHeadOnCircle(FPoint in, FPoint dir, double radius);
}
