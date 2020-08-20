package eu.scattering.core.design.engine.base.vector;

import eu.scattering.core.design.engine.base.point.FPoint;

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

    FVector moveForward(double distance) throws IllegalStateException;
    FVector moveBackward(double distance) throws IllegalStateException;

    FVector add(FVector vector);
    FVector sub(FVector vector);

    double getLength();
    FVector setLength(double length) throws IllegalStateException;

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    FVector normalize() throws IllegalStateException;

    FVector reflect(FPoint center);
    FVector reflectBase();
    FVector reflectHead();
    FVector invertDirection();

    double getInclination();
    FVector setInclination(double inclination);
    double getAzimuth();
    FVector setAzimuth(double azimuth);

    double getAngle(FPoint ref) throws IllegalStateException;
    double getAngle(FVector ref) throws IllegalStateException;

    double getDotProduct(FPoint ref);
    double getDotProduct(FVector ref);
    FVector setCrossProduct(FPoint ref);
    FVector setCrossProduct(FVector ref);

    boolean isParallel(FVector ref) throws IllegalStateException;
    FVector setParallel(FVector ref) throws IllegalStateException;
    boolean isAntiParallel(FVector ref) throws IllegalStateException;
    FVector setAntiParallel(FVector ref) throws IllegalStateException;

    boolean isOrthogonal(FVector ref) throws IllegalStateException;
    FVector setOrthogonal(FVector ref) throws IllegalStateException;
    
    boolean isNonDirectional();
}
