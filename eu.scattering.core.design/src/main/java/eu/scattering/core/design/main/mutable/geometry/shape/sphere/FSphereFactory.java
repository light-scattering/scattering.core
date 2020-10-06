package eu.scattering.core.design.main.mutable.geometry.shape.sphere;

import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;

public interface FSphereFactory {

    FSphere getFSphere();

    default FSphere getFSphere(FPoint position) {

        return getFSphere().setPosition(position);
    }

    default FSphere getFSphere(double radius) {

        return getFSphere().setRadius(radius);
    }

    default FSphere getFSphere(FPoint position, double radius) {

        return getFSphere().setPosition(position).setRadius(radius);
    }
}
