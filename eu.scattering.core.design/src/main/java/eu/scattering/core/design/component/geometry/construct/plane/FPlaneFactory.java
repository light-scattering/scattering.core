package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

public interface FPlaneFactory {

    FPlane getFPlane();

    @Modificator
    FPlane getRefFPlane(FVector refOrigin);

    //--------------------------------------------------

    default FPlane getFPlane(FPairPos3D position) {

        return getFPlane().set(position);
    }
}
