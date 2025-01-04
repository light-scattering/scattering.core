package eu.scattering.core.design.mutables.geometry.primitive.point;

import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

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

    //--------------------------------------------------

    default FPoint getFPoint(FPos3D position) {

        return getFPoint().set(position);
    }
}
