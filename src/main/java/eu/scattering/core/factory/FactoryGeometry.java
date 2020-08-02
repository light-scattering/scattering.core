package eu.scattering.core.factory;

import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.point.impl.FPoint;
import eu.scattering.core.geometry.main.base.point.impl.dec.FPointDev;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.main.base.vector.impl.FVector;
import eu.scattering.core.geometry.support.line.IFLine;
import eu.scattering.core.geometry.support.line.impl.FLine;
import eu.scattering.core.geometry.support.plane.IFPlane;
import eu.scattering.core.geometry.support.plane.impl.FPlane;

public class FactoryGeometry {

    private FactoryGeometry() { }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods returning objects of the selected implementation.
    // -------------------------------------------------------------------------------------------------

    public static IFPoint getIFPoint() {

        return FPointDev.create(FPoint.create());
    }

    public static IFVector getIFVector() {

        return FVector.create();
    }

    public static IFLine getIFLine() {

        return FLine.create();
    }

    public static IFPlane getIFPlane() {

        return FPlane.create();
    }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods which should be considered as helpers.
    // All of them base on the previous section and should be independent of the used implementation.
    // -------------------------------------------------------------------------------------------------

    public static IFPoint getIFPoint(double x) {

        return getIFPoint().setX(x);
    }

    public static IFPoint getIFPoint(double x, double y) {

        return getIFPoint().setX(x).setY(y);
    }

    public static IFPoint getIFPoint(double x, double y, double z) {

        return getIFPoint().set(x, y, z);
    }

    public static IFPoint getIFPoint(IFPoint fPoint) {

        return getIFPoint().set(fPoint);
    }

    // -------------------------------------------------------------------------------------------------

    public static IFVector getIFVector(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getIFVector().setRef(getIFPoint(bX, bY, bZ), getIFPoint(hX, hY, hZ));
    }

    public static IFVector getIFVector(IFPoint base, IFPoint head) {

        return getIFVector().setRef(base, head);
    }

    public static IFVector getIFVector(double hX, double hY, double hZ) {

        return getIFVector().setHeadRef(getIFPoint(hX, hY, hZ));
    }

    public static IFVector getIFVector(IFPoint head) {

        return getIFVector().setHeadRef(head);
    }

    public static IFVector getIFVector(IFPoint base, double hX, double hY, double hZ) {

        return getIFVector().setBaseRef(base).setHeadRef(getIFPoint(hX, hY, hZ));
    }

    public static IFVector getIFVector(double bX, double bY, double bZ, IFPoint head) {

        return getIFVector().setBaseRef(getIFPoint(bX, bY, bZ)).setHeadRef(head);
    }

    public static IFVector getIFVector(IFVector fVector) {

        return getIFVector().set(fVector);
    }

    // -------------------------------------------------------------------------------------------------

    public static IFLine getIFLine(IFVector fVector) {

        return getIFLine().setOriginRef(fVector);
    }

    // -------------------------------------------------------------------------------------------------

    public static IFPlane getIFPlane(IFVector fVector) {

        return getIFPlane().setOriginRef(fVector);
    }

}
