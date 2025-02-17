package eu.scattering.core.design.mutable.geometry.primitive.vector;

import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

public interface FVectorEngineRand {

    FVector rndAngle(FVector in, FPoint... exclusion);

    FVector rndPos(FVector in, FPairPos3D range, FPoint... exclusion);

    FVector rndPosInSphere(FVector in, double radius, FPoint... exclusion);
    FVector rndPosOnSphere(FVector in, double radius, FPoint... exclusion);
}
