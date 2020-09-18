package eu.scattering.core.design.main.algebra.engine.base.vector;

import eu.scattering.core.design.main.algebra.engine.base.Base;
import eu.scattering.core.design.main.algebra.Algebra;
import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;

public interface FVector extends Algebra<FVector>, Base<FVector>, Development<FVector> {

    FVector set(FPoint base, FPoint head);
    FVector setRef(FPoint baseRef, FPoint headRef);

    FPoint getBase();
    FVector setBase(FPoint base);
    FVector setBaseRef(FPoint baseRef);

    FPoint getHead();
    FVector setHead(FPoint head);
    FVector setHeadRef(FPoint headRef);

    FVector setSphericalCoordinates(double inclination, double azimuth);
    FVector setRandomAngle(FPoint... exclusion);

    boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ);

    FVector moveBase();
    FVector moveBase(double bX, double bY, double bZ);
    FVector moveBase(FPoint base);

    FVector moveHead();
    FVector moveHead(double hX, double hY, double hZ);
    FVector moveHead(FPoint head);

    FVector moveForward(double distance);
    FVector moveBackward(double distance);

    FVector add(FVector vector);
    FVector sub(FVector vector);

    double getLength();
    double getLengthP2();
    FVector setLength(double length);

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    FVector normalize();

    FVector reflect(FPoint center);
    FVector reflectBase();
    FVector reflectHead();
    FVector invertDirection();

    double getInclination();
    FVector setInclination(double inclination);
    double getAzimuth();
    FVector setAzimuth(double azimuth);

    double getAngle(FPoint ref);
    double getAngle(FVector ref);
    FVector setAngle(FPoint ref, double angle);
    FVector setAngle(FVector ref, double angle);

    FVector rotate(FPoint ref, double angle);
    FVector rotate(FVector ref, double angle);

    double getDotProduct(FPoint ref);
    double getDotProduct(FVector ref);
    FVector setCrossProduct(FPoint ref);
    FVector setCrossProduct(FVector ref);

    boolean isCollinear(FVector ref);

    boolean isParallel(FVector ref);
    FVector setParallel(FVector ref);

    boolean isAntiParallel(FVector ref);
    FVector setAntiParallel(FVector ref);

    boolean isOrthogonal(FVector ref);
    FVector setOrthogonal(FVector ref);

    boolean isNonDirectional();
    boolean isZero();
}
