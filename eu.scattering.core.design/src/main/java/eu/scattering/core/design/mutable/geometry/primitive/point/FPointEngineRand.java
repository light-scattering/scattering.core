package eu.scattering.core.design.mutable.geometry.primitive.point;

import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

public interface FPointEngineRand {

    FPoint rndAngle(FPoint origin, FPoint... exclusion);

    FPoint rndPosition(FPoint origin, FPairPos3D range, FPoint... exclusion);
    FPoint rndPosition(FPoint origin, double radius, FPoint... exclusion);
}
