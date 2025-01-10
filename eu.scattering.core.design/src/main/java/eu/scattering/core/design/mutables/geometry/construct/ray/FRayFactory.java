package eu.scattering.core.design.mutables.geometry.construct.ray;

import eu.scattering.core.design.annotations.Mutation;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

public interface FRayFactory {

    FRay getFRay();

    @Mutation
    FRay getRefFRay(FVector refOrigin);

    //--------------------------------------------------

    default FRay getFRay(FPairPos3D position) {

        return getFRay().set(position);
    }
}
