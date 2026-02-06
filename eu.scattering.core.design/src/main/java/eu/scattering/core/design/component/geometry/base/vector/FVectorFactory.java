package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;

public interface FVectorFactory {

    FVectorProducer getFVectorProducer();

    //--------------------------------------------------

    FVector getFVector();

    @Modificator
    FVector getRefFVector(FPoint refBase, FPoint refHead);

    @Modificator
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
