package eu.scattering.core.design.main.mutable.geometry.base.vector;

import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.mutable.geometry.base.point.FPointFactory;

public interface FVectorFactory extends FPointFactory {

    FVector getFVector();

    default FVector getFVector(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getFVector().setRef(getFPoint(bX, bY, bZ), getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(FPoint base, FPoint head) {

        return getFVector().setRef(base, head);
    }

    default FVector getFVector(double hX, double hY, double hZ) {

        return getFVector().setHeadRef(getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(FPoint head) {

        return getFVector().setHeadRef(head);
    }

    default FVector getFVector(FPoint base, double hX, double hY, double hZ) {

        return getFVector().setBaseRef(base).setHeadRef(getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(double bX, double bY, double bZ, FPoint head) {

        return getFVector().setBaseRef(getFPoint(bX, bY, bZ)).setHeadRef(head);
    }

    default FVector getFVector(FVector fVector) {

        return getFVector().set(fVector);
    }
}
