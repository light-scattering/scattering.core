package eu.scattering.core.design.elements.algebra.geometry.construct.plane;

import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;

public interface FPlaneFactory {

    FPlane getFPlane();

    default FPlane getFPlane(FVector fVector) {

        return getFPlane().setOriginRef(fVector);
    }
}
