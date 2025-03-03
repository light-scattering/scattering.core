package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointFactory;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

public interface FVectorFactory extends FPointFactory {

    FVector getFVector();

    FVector getFVector(double bX, double bY, double bZ, double hX, double hY, double hZ);

    @Modificator
    FVector getRefFVector(FPoint refHead);

    @Modificator
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
