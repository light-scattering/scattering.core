package eu.scattering.core.design.mutable.geometry.shape.sphere;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;

public interface FSphereFactory {

    FSphere getFSphere(double radius);

    FSphere getFSphere(double x, double y, double z, double radius);

    @Modificator
    FSphere getRefFSphere(FPoint refCenter, double radius);
}
