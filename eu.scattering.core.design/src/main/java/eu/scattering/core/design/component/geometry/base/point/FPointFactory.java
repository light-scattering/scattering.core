package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

public interface FPointFactory {

    FPointProducer getFPointProducer();

    //--------------------------------------------------

    FPoint getFPoint();

    //--------------------------------------------------

    default FPoint getFPoint(double x, double y, double z) {

        return getFPoint().setX(x).setY(y).setZ(z);
    }

    default FPoint getFPoint(double x, double y) {

        return getFPoint().setX(x).setY(y);
    }

    default FPoint getFPoint(double x) {

        return getFPoint().setX(x);
    }

    //--------------------------------------------------

    default FPoint getFPoint(FPos3D position) {

        return getFPoint().applyStateFrom(position);
    }
}
