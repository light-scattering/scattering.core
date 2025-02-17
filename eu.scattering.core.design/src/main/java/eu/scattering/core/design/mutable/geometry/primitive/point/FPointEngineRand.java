package eu.scattering.core.design.mutable.geometry.primitive.point;

import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

public interface FPointEngineRand {

    FPoint rndAngle(FPoint in, FPoint... exclusion);

    FPoint rndPos(FPoint in, FPairPos3D range, FPoint... exclusion);

    FPoint rndPosInSphere(FPoint in, double radius, FPoint... exclusion);
    FPoint rndPosOnSphere(FPoint in, double radius, FPoint... exclusion);
}
