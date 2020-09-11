package eu.scattering.core.design.main.box.rotation;

import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;

public interface FRotationFactory {

    FRotation getFRotation(FVector axis, double angle);

    FRotation getFRotation(FPoint axis, double angle);

    FRotation getFRotation(String structure);
}
