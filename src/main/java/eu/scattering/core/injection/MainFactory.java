package eu.scattering.core.injection;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.implementation.main.engine.base.point.FPointDefault;
import eu.scattering.core.implementation.main.engine.base.point.FPointDevelopment;
import eu.scattering.core.design.main.engine.base.vector.FVector;
import eu.scattering.core.implementation.main.engine.base.vector.FVectorDefault;
import eu.scattering.core.implementation.main.engine.base.vector.FVectorDevelopment;
import eu.scattering.core.design.main.engine.support.line.FLine;
import eu.scattering.core.implementation.main.engine.support.line.FLineDefault;
import eu.scattering.core.implementation.main.engine.support.line.FLineDevelopment;
import eu.scattering.core.design.main.engine.support.plane.FPlane;
import eu.scattering.core.implementation.main.engine.support.plane.FPlaneDefault;
import eu.scattering.core.implementation.main.engine.support.plane.FPlaneDevelopment;

public class MainFactory {

    private MainFactory() { }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods returning objects of the selected implementation.
    // -------------------------------------------------------------------------------------------------

    public static FPoint getFPoint() {
        FPoint fPoint = FPointDefault.create();

        fPoint = Config.isDevEnabled() ? FPointDevelopment.create(fPoint) : fPoint;

        return fPoint;
    }

    public static FVector getFVector() {
        FVector fVector = FVectorDefault.create();

        fVector = Config.isDevEnabled() ? FVectorDevelopment.create(fVector) : fVector;

        return fVector;
    }

    public static FLine getFLine() {
        FLine fLine = FLineDefault.create();

        fLine = Config.isDevEnabled() ? FLineDevelopment.create(fLine) : fLine;

        return fLine;
    }

    public static FPlane getFPlane() {
        FPlane fPlane = FPlaneDefault.create();

        fPlane = Config.isDevEnabled() ? FPlaneDevelopment.create(fPlane) : fPlane;

        return fPlane;
    }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods which should be considered as helpers.
    // All of them base on the previous section and should be independent of the used implementation.
    // -------------------------------------------------------------------------------------------------

    public static FPoint getFPoint(double x) {

        return getFPoint().setX(x);
    }

    public static FPoint getFPoint(double x, double y) {

        return getFPoint().setX(x).setY(y);
    }

    public static FPoint getFPoint(double x, double y, double z) {

        return getFPoint().set(x, y, z);
    }

    public static FPoint getFPoint(FPoint fPoint) {

        return getFPoint().set(fPoint);
    }

    // -------------------------------------------------------------------------------------------------

    public static FVector getFVector(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getFVector().setRef(getFPoint(bX, bY, bZ), getFPoint(hX, hY, hZ));
    }

    public static FVector getFVector(FPoint base, FPoint head) {

        return getFVector().setRef(base, head);
    }

    public static FVector getFVector(double hX, double hY, double hZ) {

        return getFVector().setHeadRef(getFPoint(hX, hY, hZ));
    }

    public static FVector getFVector(FPoint head) {

        return getFVector().setHeadRef(head);
    }

    public static FVector getFVector(FPoint base, double hX, double hY, double hZ) {

        return getFVector().setBaseRef(base).setHeadRef(getFPoint(hX, hY, hZ));
    }

    public static FVector getFVector(double bX, double bY, double bZ, FPoint head) {

        return getFVector().setBaseRef(getFPoint(bX, bY, bZ)).setHeadRef(head);
    }

    public static FVector getFVector(FVector fVector) {

        return getFVector().set(fVector);
    }

    // -------------------------------------------------------------------------------------------------

    public static FLine getFLine(FVector fVector) {

        return getFLine().setOriginRef(fVector);
    }

    // -------------------------------------------------------------------------------------------------

    public static FPlane getFPlane(FVector fVector) {

        return getFPlane().setOriginRef(fVector);
    }

}
