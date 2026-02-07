package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos3D;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;

public interface FPlaneFactory {

    FPlaneProducer getFPlaneProducer();

    FPlaneHelper getFPlaneHelper();

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
