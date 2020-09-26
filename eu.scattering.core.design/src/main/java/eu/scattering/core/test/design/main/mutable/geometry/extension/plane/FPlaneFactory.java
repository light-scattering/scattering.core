package eu.scattering.core.test.design.main.mutable.geometry.extension.plane;

import eu.scattering.core.test.design.main.mutable.geometry.base.vector.FVector;

public interface FPlaneFactory {

    FPlane getFPlane();

    default FPlane getFPlane(FVector fVector) {

        return getFPlane().setOriginRef(fVector);
    }
}
