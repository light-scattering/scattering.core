package eu.scattering.core.factory;

import eu.scattering.core.Config;
import eu.scattering.core.logic.main.engine.base.point.FPoint;
import eu.scattering.core.impl.main.engine.base.point.FPointDefault;
import eu.scattering.core.impl.main.engine.base.point.FPointDev;
import eu.scattering.core.logic.main.engine.base.vector.FVector;
import eu.scattering.core.impl.main.engine.base.vector.FVectorDefault;
import eu.scattering.core.impl.main.engine.base.vector.FVectorDev;
import eu.scattering.core.logic.main.engine.extension.line.FLine;
import eu.scattering.core.impl.main.engine.extension.line.FLineDefault;
import eu.scattering.core.impl.main.engine.extension.line.FLineDev;
import eu.scattering.core.logic.main.engine.extension.plane.FPlane;
import eu.scattering.core.impl.main.engine.extension.plane.FPlaneDefault;
import eu.scattering.core.impl.main.engine.extension.plane.FPlaneDev;

public class MainFactory {

    private MainFactory() { }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods returning objects of the selected implementation.
    // -------------------------------------------------------------------------------------------------

    public static FPoint getIFPoint() {
        FPoint fPoint = FPointDefault.create();

        fPoint = Config.isDevEnabled() ? FPointDev.create(fPoint) : fPoint;

        return fPoint;
    }

    public static FVector getIFVector() {
        FVector fVector = FVectorDefault.create();

        fVector = Config.isDevEnabled() ? FVectorDev.create(fVector) : fVector;

        return fVector;
    }

    public static FLine getIFLine() {
        FLine fLine = FLineDefault.create();

        fLine = Config.isDevEnabled() ? FLineDev.create(fLine) : fLine;

        return fLine;
    }

    public static FPlane getIFPlane() {
        FPlane fPlane = FPlaneDefault.create();

        fPlane = Config.isDevEnabled() ? FPlaneDev.create(fPlane) : fPlane;

        return fPlane;
    }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods which should be considered as helpers.
    // All of them base on the previous section and should be independent of the used implementation.
    // -------------------------------------------------------------------------------------------------

    public static FPoint getIFPoint(double x) {

        return getIFPoint().setX(x);
    }

    public static FPoint getIFPoint(double x, double y) {

        return getIFPoint().setX(x).setY(y);
    }

    public static FPoint getIFPoint(double x, double y, double z) {

        return getIFPoint().set(x, y, z);
    }

    public static FPoint getIFPoint(FPoint fPoint) {

        return getIFPoint().set(fPoint);
    }

    // -------------------------------------------------------------------------------------------------

    public static FVector getIFVector(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getIFVector().setRef(getIFPoint(bX, bY, bZ), getIFPoint(hX, hY, hZ));
    }

    public static FVector getIFVector(FPoint base, FPoint head) {

        return getIFVector().setRef(base, head);
    }

    public static FVector getIFVector(double hX, double hY, double hZ) {

        return getIFVector().setHeadRef(getIFPoint(hX, hY, hZ));
    }

    public static FVector getIFVector(FPoint head) {

        return getIFVector().setHeadRef(head);
    }

    public static FVector getIFVector(FPoint base, double hX, double hY, double hZ) {

        return getIFVector().setBaseRef(base).setHeadRef(getIFPoint(hX, hY, hZ));
    }

    public static FVector getIFVector(double bX, double bY, double bZ, FPoint head) {

        return getIFVector().setBaseRef(getIFPoint(bX, bY, bZ)).setHeadRef(head);
    }

    public static FVector getIFVector(FVector fVector) {

        return getIFVector().set(fVector);
    }

    // -------------------------------------------------------------------------------------------------

    public static FLine getIFLine(FVector fVector) {

        return getIFLine().setOriginRef(fVector);
    }

    // -------------------------------------------------------------------------------------------------

    public static FPlane getIFPlane(FVector fVector) {

        return getIFPlane().setOriginRef(fVector);
    }

}
