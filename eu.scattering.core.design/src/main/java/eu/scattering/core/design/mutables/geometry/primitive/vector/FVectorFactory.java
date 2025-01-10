package eu.scattering.core.design.mutables.geometry.primitive.vector;

import eu.scattering.core.design.annotations.Mutation;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPointFactory;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

public interface FVectorFactory extends FPointFactory {

    FVector getFVector();

    @Mutation
    FVector getRefFVector(FPoint refBase, FPoint refHead);

    @Mutation
    FVector getRefFVector(FPoint refHead);

    //--------------------------------------------------

    default FVector getFVector(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getFVector().set(bX, bY, bZ, hX, hY, hZ);
    }

    default FVector getFVector(double hX, double hY, double hZ) {

        return getFVector().setHead(hX, hY, hZ);
    }

    default FVector getFVector(FPoint base, FPoint head) {

        return getFVector().set(base, head);
    }

    default FVector getFVector(FPoint head) {

        return getFVector().setHead(head);
    }

    //--------------------------------------------------

    default FVector getFVector(FPairPos3D position) {

        return getFVector().set(position);
    }
}
