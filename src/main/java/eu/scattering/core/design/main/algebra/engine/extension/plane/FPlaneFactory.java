package eu.scattering.core.design.main.algebra.engine.extension.plane;

import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;

public interface FPlaneFactory {

    FPlane getFPlane();

    default FPlane getFPlane(FVector fVector) {

        return getFPlane().setOriginRef(fVector);
    }
}
