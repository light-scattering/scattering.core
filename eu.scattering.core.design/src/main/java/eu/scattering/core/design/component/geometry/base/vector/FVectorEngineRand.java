package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

public interface FVectorEngineRand {

    FVector rndAngle(FVector in, FPoint... exclusion);

    FVector rndPos(FVector in, FPairPos3D range, FPoint... exclusion);

    FVector rndPosInSphere(FVector in, double radius, FPoint... exclusion);
    FVector rndPosOnSphere(FVector in, double radius, FPoint... exclusion);
}
