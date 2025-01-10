package eu.scattering.core.design.mutables.geometry.construct.plane;

import eu.scattering.core.design.annotations.Mutation;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

public interface FPlaneFactory {

    FPlane getFPlane();

    @Mutation
    FPlane getRefFPlane(FVector refOrigin);

    //--------------------------------------------------

    default FPlane getFPlane(FPairPos3D position) {

        return getFPlane().set(position);
    }
}
