package eu.scattering.core.design.mutable.geometry.construct.segment;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.mutable.geometry.primitive.vector.FVector;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

public interface FSegmentFactory {

    FSegment getFSegment();

    @Modificator
    FSegment getRefFSegment(FVector refOrigin);

    //--------------------------------------------------

    default FSegment getFSegment(FPairPos3D position) {

        return getFSegment().set(position);
    }
}
