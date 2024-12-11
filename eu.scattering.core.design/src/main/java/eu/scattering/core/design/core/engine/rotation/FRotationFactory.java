package eu.scattering.core.design.core.engine.rotation;

import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.core.algebra.geometry.primitive.vector.FVector;

public interface FRotationFactory {

    FRotation getFRotation(FVector axis, double angle);
    FRotation getFRotation(FPoint axis, double angle);
    FRotation getFRotation(String structure);
}
