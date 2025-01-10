package eu.scattering.core.design.mutables.geometry.construct.segment;

import eu.scattering.core.design.annotations.Mutation;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

public interface FSegmentFactory {

    FSegment getFSegment();

    @Mutation
    FSegment getRefFSegment(FVector refOrigin);

    //--------------------------------------------------

    default FSegment getFSegment(FPairPos3D position) {

        return getFSegment().set(position);
    }
}
