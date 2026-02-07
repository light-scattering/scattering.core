package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;

public interface FRayFactory {

    FRayProducer getFRayProducer();

    FRayHelper getFRayHelper();

    //--------------------------------------------------

    FRay getFRay();

    @Modificator
    FRay getRefFRay(FVector refOrigin);

    //--------------------------------------------------

    @Modificator
    default FRay getRefFRay(Construct<?> construct) {

        return getRefFRay(construct.getRefOrigin());
    }

    default FRay getFRay(FPairPos3D position) {

        return getFRay().set(position);
    }
}
