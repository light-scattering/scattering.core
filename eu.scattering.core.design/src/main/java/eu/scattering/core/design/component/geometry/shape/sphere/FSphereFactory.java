package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.point.FPoint;

public interface FSphereFactory {

    FSphereProducer getFSphereProducer();

    //--------------------------------------------------

    FSphere getFSphere();

    @Modificator
    FSphere getRefFSphere(FPoint refCenter);

    //--------------------------------------------------

    default FSphere getFSphere(double radius) {

        return (FSphere) getFSphere().setRadius(radius);
    }

    default FSphere getFSphere(double x, double y, double z) {

        return (FSphere) getFSphere().setCenter(x, y, z);
    }

    default FSphere getFSphere(double x, double y, double z, double radius) {

        return (FSphere) getFSphere().setCenter(x, y, z).setRadius(radius);
    }

    @Modificator
    default FSphere getRefFSphere(FPoint refCenter, double radius) {

        return (FSphere) getRefFSphere(refCenter).setRadius(radius);
    }
}
