package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.utility.annotation.Fragment;

public interface FPointAspectRand {

    FPoint withinRange(FPoint in, FPairPos3D range);

    FPoint intoSphere(FPoint in);
    FPoint intoSphere(FPoint in, double radius);

    FPoint ontoSphere(FPoint in);
    FPoint ontoSphere(FPoint in, double radius);

    FPoint ontoAxis(FPoint in);
    FPoint ontoAxis(FPoint in, FPoint axis);

    @Fragment
    FPoint intoCircleOrthogonalToBase(FPoint in, FPoint dir, double radius);
    @Fragment
    FPoint ontoCircleOrthogonalToBase(FPoint in, FPoint dir, double radius);
    @Fragment
    FPoint intoCircleOrthogonalToHead(FPoint in, FPoint dir, double radius);
    @Fragment
    FPoint ontoCircleOrthogonalToHead(FPoint in, FPoint dir, double radius);
}
