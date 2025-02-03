package eu.scattering.core.design.mutable.geometry.primitive.vector;

import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

public interface FVectorEngineRand {

    FVector rndAngle(FVector origin, FPoint... exclusion);

    FVector rndPosition(FVector origin, FPairPos3D range, FPoint... exclusion);
    FVector rndPosition(FVector origin, double radius, FPoint... exclusion);
}
