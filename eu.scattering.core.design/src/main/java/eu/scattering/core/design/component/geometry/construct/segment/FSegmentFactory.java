package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;

public interface FSegmentFactory {

    FSegmentProducer getFSegmentProducer();

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
