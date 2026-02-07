package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos3D;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;

public interface FSegmentFactory {

    FSegmentProducer getFSegmentProducer();

    FSegmentHelper getFSegmentHelper();

    //--------------------------------------------------

    FSegment getFSegment();

    @Modificator
    FSegment getRefFSegment(FVector refOrigin);

    //--------------------------------------------------

    @Modificator
    default FSegment getRefFSegment(Construct<?> construct) {

        return getRefFSegment(construct.getRefOrigin());
    }

    default FSegment getFSegment(FPairPos3D position) {

        return getFSegment().set(position);
    }
}
