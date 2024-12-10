package eu.scattering.core.design.core.mutable.geometry.shape.sphere;

import eu.scattering.core.design.core.mutable.geometry.simple.point.FPoint;

public interface FSphereFactory {

    FSphere getFSphere();

    default FSphere getFSphere(FPoint position) {

        return getFSphere().setCenter(position);
    }

    default FSphere getFSphere(double radius) {

        return getFSphere().setOuterRadius(radius);
    }

    default FSphere getFSphere(FPoint position, double radius) {

        return getFSphere().setCenter(position).setOuterRadius(radius);
    }
}
