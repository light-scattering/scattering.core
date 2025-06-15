package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

public interface FPlaneFactory {

    FPlaneProducer getFPlaneProducer();

    //--------------------------------------------------

    FPlane getFPlane();

    @Modificator
    FPlane getRefFPlane(FVector refOrigin);

    //--------------------------------------------------

    @Modificator
    default FPlane getRefFPlane(Construct<?> construct) {

        return getRefFPlane(construct.getRefOrigin());
    }

    default FPlane getFPlane(FPairPos3D position) {

        return getFPlane().set(position);
    }
}
