package eu.scattering.core.design.main.algebra.engine.base.vector;

import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;

public interface FVectorAdvanced {

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

    double getDotProduct(FPoint ref);
    double getDotProduct(FVector ref);
    FVector setCrossProduct(FPoint ref);
    FVector setCrossProduct(FVector ref);

//    boolean isEquidirectional(FVector ref);
//    boolean isEquidirectional(FPoint ref);

    boolean isParallel(FVector ref);
//    boolean isParallel(FPoint ref);
    FVector setParallel(FVector ref);

    boolean isAntiParallel(FVector ref);
//    boolean isAntiParallel(FPoint ref);
    FVector setAntiParallel(FVector ref);

    boolean isOrthogonal(FVector ref);
//    boolean isOrthogonal(FPoint ref);
    FVector setOrthogonal(FVector ref);
    
    boolean isNonDirectional();
//    boolean isZero();
}
