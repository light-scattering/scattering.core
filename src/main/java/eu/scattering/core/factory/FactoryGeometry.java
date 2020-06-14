package eu.scattering.core.factory;

import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.base.point.impl.FPoint;
import eu.scattering.core.geometry.base.vector.IFVector;
import eu.scattering.core.geometry.base.vector.impl.FVector;

public class FactoryGeometry {

    private FactoryGeometry() { }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods returning objects of the selected implementation.
    // -------------------------------------------------------------------------------------------------

    public static IFPoint getIFPoint() {
        return FPoint.create();
    }

    public static IFVector getIFVector() {
        return FVector.create();
    }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods which should be considered as helpers.
    // All of them base on the previous section and should be independent of the used implementation.
    // -------------------------------------------------------------------------------------------------

    public static IFPoint getIFPoint(double x) { return getIFPoint().setX(x); }

    public static IFPoint getIFPoint(double x, double y) { return getIFPoint().setX(x).setY(y); }

    public static IFPoint getIFPoint(double x, double y, double z) {
        return getIFPoint().set(x, y, z);
    }

    public static IFPoint getIFPoint(IFPoint fPoint) { return getIFPoint().set(fPoint); }

    public static IFVector getIFVector(IFPoint head) {
        return getIFVector().setHeadRef(head);
    }

    public static IFVector getIFVector(IFPoint base, IFPoint head) {
        return getIFVector().setRef(base, head);
    }

    public static IFVector getIFVector(IFVector fVector) {
        return getIFVector().set(fVector);
    }
}
