package eu.scattering.core.test.design.main.fixed.rotation;

import eu.scattering.core.test.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.test.design.main.mutable.geometry.base.vector.FVector;

public interface FRotationFactory {

    FRotation getFRotation(FVector axis, double angle);
    FRotation getFRotation(FPoint axis, double angle);
    FRotation getFRotation(String structure);
}
