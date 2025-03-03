package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

public interface FRayFactory {

    FRay getFRay();

    @Modificator
    FRay getRefFRay(FVector refOrigin);

    //--------------------------------------------------

    default FRay getFRay(FPairPos3D position) {

        return getFRay().set(position);
    }
}
