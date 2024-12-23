package eu.scattering.core.design.elements.engine.rotation;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;

public interface FRotationFactory {

    FRotation getFRotation(FPoint axis, double angle);
    FRotation getFRotation(FVector axis, double angle);
}
