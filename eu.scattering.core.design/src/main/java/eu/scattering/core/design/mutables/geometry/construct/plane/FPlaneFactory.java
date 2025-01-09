package eu.scattering.core.design.mutables.geometry.construct.plane;

import eu.scattering.core.design.annotations.Mutable;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

public interface FPlaneFactory {

    FPlane getFPlane();

    @Mutable
    FPlane getRefFPlane(FVector refOrigin);

    //--------------------------------------------------

    default FPlane getFPlane(FPairPos3D position) {

        return getFPlane().set(position);
    }
}
