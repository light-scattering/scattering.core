package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

public interface FPointEngineRand {

    FPoint rndAngle(FPoint in);

    FPoint rndPosInRange(FPoint in, FPairPos3D range);

    FPoint rndPosInSphere(FPoint in, double radius);
    FPoint rndPosOnSphere(FPoint in, double radius);

    FPoint rndPosInCircle(FPoint in, FPoint dir, double radius);
//    FPoint rndPosOnCircle(FPoint in, FPoint dir, double radius);
}
