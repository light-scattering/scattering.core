package eu.scattering.core.design.mutables.geometry.primitive.vector;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

public interface FVectorEngineRandom {

    FVector rndAngle(FVector origin, FPoint... exclusion);

    FVector rndPosition(FVector origin, FPairPos3D range, FPoint... exclusion);
    FVector rndPosition(FVector origin, double radius, FPoint... exclusion);
}
