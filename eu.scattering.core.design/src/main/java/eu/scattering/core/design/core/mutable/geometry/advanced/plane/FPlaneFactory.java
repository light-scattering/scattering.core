package eu.scattering.core.design.core.mutable.geometry.advanced.plane;

import eu.scattering.core.design.core.mutable.geometry.simple.vector.FVector;

public interface FPlaneFactory {

    FPlane getFPlane();

    default FPlane getFPlane(FVector fVector) {

        return getFPlane().setOriginRef(fVector);
    }
}
