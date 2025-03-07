package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.point.FPoint;

public interface FSphereFactory {

    FSphere getFSphere();

    FSphere getFSphere(double radius);

    FSphere getFSphere(double x, double y, double z);

    FSphere getFSphere(double x, double y, double z, double radius);

    @Modificator
    FSphere getRefFSphere(FPoint refCenter);

    @Modificator
    FSphere getRefFSphere(FPoint refCenter, double radius);
}
