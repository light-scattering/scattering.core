package eu.scattering.core.design.mutables.algebra.geometry.construct.plane;

import eu.scattering.core.design.mutables.algebra.geometry.primitive.vector.FVector;

public interface FPlaneFactory {

    FPlane getFPlane();

    default FPlane getFPlane(FVector fVector) {

        return getFPlane().setOriginRef(fVector);
    }
}
