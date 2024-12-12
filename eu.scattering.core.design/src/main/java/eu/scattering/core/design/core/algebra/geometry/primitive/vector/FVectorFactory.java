package eu.scattering.core.design.core.algebra.geometry.primitive.vector;

import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPointFactory;

public interface FVectorFactory extends FPointFactory {

    FVector getFVector();

    //--------------------------------------------------

    default FVector getFVector(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getFVector().setRef(getFPoint(bX, bY, bZ), getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(FPoint base, FPoint head) {

        return getFVector().setRef(base, head);
    }

    default FVector getFVector(double hX, double hY, double hZ) {

        return getFVector().setRefHead(getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(FPoint head) {

        return getFVector().setRefHead(head);
    }

    default FVector getFVector(FPoint base, double hX, double hY, double hZ) {

        return getFVector().setRefBase(base).setRefHead(getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(double bX, double bY, double bZ, FPoint head) {

        return getFVector().setRefBase(getFPoint(bX, bY, bZ)).setRefHead(head);
    }

    default FVector getFVector(FVector fVector) {

        return getFVector().set(fVector);
    }
}
