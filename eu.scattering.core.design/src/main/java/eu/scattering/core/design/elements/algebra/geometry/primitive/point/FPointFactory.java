package eu.scattering.core.design.elements.algebra.geometry.primitive.point;

import eu.scattering.core.design.elements.data.position.FPos3D;

public interface FPointFactory {

    FPoint getFPoint();

    //--------------------------------------------------

    default FPoint getFPoint(double x) {

        return getFPoint().setX(x);
    }

    default FPoint getFPoint(double x, double y) {

        return getFPoint().setX(x).setY(y);
    }

    default FPoint getFPoint(double x, double y, double z) {

        return getFPoint().set(x, y, z);
    }

    default FPoint getFPoint(FPoint fPoint) {

        return getFPoint().set(fPoint);
    }

    default FPoint getFPoint(FPos3D position) {

        return getFPoint().set(position);
    }
}
