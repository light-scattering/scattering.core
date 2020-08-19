package eu.scattering.core.main;

import eu.scattering.core.Config;
import eu.scattering.core.main.engine.base.point.IFPoint;
import eu.scattering.core.main.engine.base.point.impl.FPoint;
import eu.scattering.core.main.engine.base.point.impl.dec.FPointDev;
import eu.scattering.core.main.engine.base.vector.IFVector;
import eu.scattering.core.main.engine.base.vector.impl.FVector;
import eu.scattering.core.main.engine.base.vector.impl.dec.FVectorDev;
import eu.scattering.core.main.engine.extension.line.IFLine;
import eu.scattering.core.main.engine.extension.line.impl.FLine;
import eu.scattering.core.main.engine.extension.line.impl.dec.FLineDev;
import eu.scattering.core.main.engine.extension.plane.IFPlane;
import eu.scattering.core.main.engine.extension.plane.impl.FPlane;
import eu.scattering.core.main.engine.extension.plane.impl.dec.FPlaneDev;

public class MainFactory {

    private MainFactory() { }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods returning objects of the selected implementation.
    // -------------------------------------------------------------------------------------------------

    public static IFPoint getIFPoint() {
        IFPoint fPoint = FPoint.create();

        fPoint = Config.isDevEnabled() ? FPointDev.create(fPoint) : fPoint;

        return fPoint;
    }

    public static IFVector getIFVector() {
        IFVector fVector = FVector.create();

        fVector = Config.isDevEnabled() ? FVectorDev.create(fVector) : fVector;

        return fVector;
    }

    public static IFLine getIFLine() {
        IFLine fLine = FLine.create();

        fLine = Config.isDevEnabled() ? FLineDev.create(fLine) : fLine;

        return fLine;
    }

    public static IFPlane getIFPlane() {
        IFPlane fPlane = FPlane.create();

        fPlane = Config.isDevEnabled() ? FPlaneDev.create(fPlane) : fPlane;

        return fPlane;
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
