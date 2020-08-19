package eu.scattering.core.design.main.engine.base.vector;

import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.support.exception.PositionException;
import eu.scattering.core.design.main.engine.base.point.FPoint;

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

    FVector moveForward(double distance) throws DirectionException;
    FVector moveBackward(double distance) throws DirectionException;

    FVector add(FVector vector);
    FVector sub(FVector vector);

    double getLength();
    FVector setLength(double length) throws DirectionException;

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    FVector normalize() throws DirectionException;

    FVector reflect(FPoint center);
    FVector reflectBase();
    FVector reflectHead();
    FVector invertDirection();

    double getInclination();
    FVector setInclination(double inclination);
    double getAzimuth();
    FVector setAzimuth(double azimuth);

    double getAngle(FPoint ref) throws PositionException, DirectionException;
    double getAngle(FVector ref) throws DirectionException;

    double getDotProduct(FPoint ref);
    double getDotProduct(FVector ref);
    FVector setCrossProduct(FPoint ref);
    FVector setCrossProduct(FVector ref);

    boolean isParallel(FVector ref) throws DirectionException;
    FVector setParallel(FVector ref) throws DirectionException;
    boolean isAntiParallel(FVector ref) throws DirectionException;
    FVector setAntiParallel(FVector ref) throws DirectionException;

    boolean isOrthogonal(FVector ref) throws DirectionException;
    FVector setOrthogonal(FVector ref) throws PositionException, DirectionException;
    
    boolean isNonDirectional();
}
