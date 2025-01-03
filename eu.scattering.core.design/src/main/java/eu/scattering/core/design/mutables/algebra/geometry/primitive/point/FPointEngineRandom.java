package eu.scattering.core.design.mutables.algebra.geometry.primitive.point;

import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

public interface FPointEngineRandom {

    FPoint rndAngle(FPoint origin, FPoint... exclusion);

    FPoint rndPosition(FPoint origin, FPairPos3D range, FPoint... exclusion);
    FPoint rndPosition(FPoint origin, double radius, FPoint... exclusion);
}
