package eu.scattering.core.design.injection;

import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import eu.scattering.core.design.main.algebra.engine.extension.plane.FPlane;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;

public interface MainFactory extends MainFactoryValjo {

    FPoint getFPoint();

    default FPoint getFPoint(double x) {

        return getFPoint().setX(x);
    }

    default FPoint getFPoint(double x, double y) {

        return getFPoint().setX(x).setY(y);
    }

    default FPoint getFPoint(double x, double y, double z) {

        return getFPoint().set(x, y, z);
    }

    default FPoint getFPoint(FPoint fPoint) {

        return getFPoint().set(fPoint);
    }

    // -------------------------------------------------------------------------------------------------

    FVector getFVector();

    default FVector getFVector(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getFVector().setRef(getFPoint(bX, bY, bZ), getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(FPoint base, FPoint head) {

        return getFVector().setRef(base, head);
    }

    default FVector getFVector(double hX, double hY, double hZ) {

        return getFVector().setHeadRef(getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(FPoint head) {

        return getFVector().setHeadRef(head);
    }

    default FVector getFVector(FPoint base, double hX, double hY, double hZ) {

        return getFVector().setBaseRef(base).setHeadRef(getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(double bX, double bY, double bZ, FPoint head) {

        return getFVector().setBaseRef(getFPoint(bX, bY, bZ)).setHeadRef(head);
    }

    default FVector getFVector(FVector fVector) {

        return getFVector().set(fVector);
    }

    // -------------------------------------------------------------------------------------------------

    FLine getFLine();

    default FLine getFLine(FVector fVector) {

        return getFLine().setOriginRef(fVector);
    }

    // -------------------------------------------------------------------------------------------------

    FPlane getFPlane();

    default FPlane getFPlane(FVector fVector) {

        return getFPlane().setOriginRef(fVector);
    }

    // -------------------------------------------------------------------------------------------------

    FComplex getFComplex();

    default FComplex getFComplex(double re, double im) {

        return getFComplex().set(re, im);
    }

    // -------------------------------------------------------------------------------------------------

    FQuaternion getFQuaternion();

    default FQuaternion getFQuaternion(double re, double i, double j, double k) {

        return getFQuaternion().set(re, i, j, k);
    }
}
