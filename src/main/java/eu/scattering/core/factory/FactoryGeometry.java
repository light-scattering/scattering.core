package eu.scattering.core.factory;

import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.base.point.impl.FPoint;

public class FactoryGeometry {

    private FactoryGeometry() { }

    public static IFPoint getIFPoint() {
        return FPoint.create();
    }

    public static IFPoint getIFPoint(double x, double y, double z) {
        return FPoint.create().set(x, y, z);
    }

}
