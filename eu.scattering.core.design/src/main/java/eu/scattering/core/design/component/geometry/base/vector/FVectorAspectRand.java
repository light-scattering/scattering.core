package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.utility.annotation.Fragment;
import eu.scattering.core.design.component.geometry.base.point.FPoint;

public interface FVectorAspectRand {

    FVector intoSphere(FVector in);
    FVector ontoSphere(FVector in);

    FPoint ontoAxis(FPoint in, FVector axis);

    @Fragment
    FPoint intoCircleOrthogonalToBase(FPoint in, FVector dir, double radius);
    @Fragment
    FPoint ontoCircleOrthogonalToBase(FPoint in, FVector dir, double radius);
    @Fragment
    FPoint intoCircleOrthogonalToHead(FPoint in, FVector dir, double radius);
    @Fragment
    FPoint ontoCircleOrthogonalToHead(FPoint in, FVector dir, double radius);
}
