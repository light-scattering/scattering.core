package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.transfer.container.position.FPos3D.FPos3D;

public interface FPointFactory {

    FPoint getFPoint();

    FPoint getFPoint(double x, double y, double z);

    //--------------------------------------------------

    default FPoint getFPoint(double x) {

        return getFPoint(x, 0, 0);
    }

    default FPoint getFPoint(double x, double y) {

        return getFPoint(x, y, 0);
    }

    //--------------------------------------------------

    default FPoint getFPoint(FPos3D position) {

        return getFPoint(position.getD0(), position.getD1(), position.getD2());
    }
}
