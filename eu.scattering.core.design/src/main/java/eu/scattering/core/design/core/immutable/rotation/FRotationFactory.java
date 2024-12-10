package eu.scattering.core.design.core.immutable.rotation;

import eu.scattering.core.design.core.mutable.geometry.simple.point.FPoint;
import eu.scattering.core.design.core.mutable.geometry.simple.vector.FVector;

public interface FRotationFactory {

    FRotation getFRotation(FVector axis, double angle);
    FRotation getFRotation(FPoint axis, double angle);
    FRotation getFRotation(String structure);
}
