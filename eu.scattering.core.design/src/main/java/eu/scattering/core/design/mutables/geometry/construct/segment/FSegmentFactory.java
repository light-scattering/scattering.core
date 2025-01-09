package eu.scattering.core.design.mutables.geometry.construct.segment;

import eu.scattering.core.design.annotations.Mutable;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

public interface FSegmentFactory {

    FSegment getFSegment();

    @Mutable
    FSegment getRefFSegment(FVector refOrigin);

    //--------------------------------------------------

    default FSegment getFSegment(FPairPos3D position) {

        return getFSegment().set(position);
    }
}
