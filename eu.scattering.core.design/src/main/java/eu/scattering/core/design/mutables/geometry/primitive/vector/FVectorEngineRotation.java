package eu.scattering.core.design.mutables.algebra.geometry.primitive.vector;

import eu.scattering.core.design.mutables.algebra.geometry.primitive.point.FPoint;

public interface FVectorEngineRotation {

    FVector setAngle(FVector origin, FVector ref, double angle);

    FVector rotate(FVector origin, FPoint ref, double angle);
    FVector rotate(FVector origin, FVector ref, double angle);
}
