package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

public interface FSegmentFactory {

    FSegment getFSegment();

    @Modificator
    FSegment getRefFSegment(FVector refOrigin);

    //--------------------------------------------------

    default FSegment getFSegment(FVector refOrigin) {

        return getRefFSegment(refOrigin.copy());
    }

    default FSegment getFSegment(Construct<?> construct) {

        return getRefFSegment(construct.getRefOrigin().copy());
    }

    default FSegment getFSegment(FPairPos3D position) {

        return getFSegment().set(position);
    }

    @Modificator
    default FSegment getRefFSegment(Construct<?> construct) {

        return getRefFSegment(construct.getRefOrigin());
    }
}
