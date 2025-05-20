package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

public interface FVectorEngineRand {

    FVector rndAngle(FVector in);
//    FVector rndMagnitude(FVector in);

    FVector rndPos(FVector in, FPairPos3D range);

    FVector rndPosInSphere(FVector in, double radius);
    FVector rndPosOnSphere(FVector in, double radius);

//    FVector rndPosInCircle(FPoint in, FVector dir, double radius);
//    FVector rndPosOnCircle(FPoint in, FVector dir, double radius);
}
