package eu.scattering.core.design.mutables.geometry.primitive.vector;

import eu.scattering.core.design.annotations.Mutation;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPointFactory;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

public interface FVectorFactory extends FPointFactory {

    FVector getFVector();

    FVector getFVector(double bX, double bY, double bZ, double hX, double hY, double hZ);

    @Mutation
    FVector getRefFVector(FPoint refHead);

    @Mutation
    FVector getRefFVector(FPoint refBase, FPoint refHead);

    //--------------------------------------------------

    default FVector getFVector(double hX, double hY, double hZ) {

        return getFVector(0, 0, 0, hX, hY, hZ);
    }

    default FVector getFVector(FPoint base, FPoint head) {

        return getFVector(base.getX(), base.getY(), base.getZ(), head.getX(), head.getY(), head.getZ());
    }

    default FVector getFVector(FPoint head) {

        return getFVector(0, 0, 0, head.getX(), head.getY(), head.getZ());
    }

    //--------------------------------------------------

    default FVector getFVector(FPairPos3D position) {
        var base = position.getPosA();
        var head = position.getPosB();

        return getFVector(base.getD0(), base.getD1(), base.getD2(), head.getD0(), head.getD1(), head.getD2());
    }
}
