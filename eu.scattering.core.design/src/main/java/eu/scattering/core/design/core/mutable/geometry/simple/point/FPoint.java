package eu.scattering.core.design.core.mutable.geometry.simple.point;

import eu.scattering.core.design.core.mutable.geometry.simple.Simple;
import eu.scattering.core.design.core.mutable.Mutable;
import eu.scattering.core.design.debug.Debug;

public interface FPoint extends Simple<FPoint> {

    FPoint set(double x, double y, double z);

    double getX();
    FPoint setX(double x);

    double getY();
    FPoint setY(double y);

    double getZ();
    FPoint setZ(double z);

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    boolean isZero();
    boolean isNonDirectional();

    FPoint normalize();

    FPoint reflect();
    FPoint reflect(FPoint ref);

    double getLength();
    double getLengthP2();
    FPoint setLength(double length);

    double getDistance(FPoint ref);
    double getDistanceP2(FPoint ref);
    FPoint setDistance(FPoint ref, double distance);

    double getAngle(FPoint ref);
    FPoint setAngle(FPoint ref, double angle);

    double getDotProduct(FPoint ref);

    FPoint setCrossProduct(FPoint ref);

    FPoint rotate(FPoint ref, double angle);

    //--------------------------------------------------
    // Spherical coordinates
    //--------------------------------------------------

    FPoint setSphericalCoordinates(double inclination, double azimuth);

    double getInclination();
    FPoint setInclination(double inclination);

    double getAzimuth();
    FPoint setAzimuth(double azimuth);

    //--------------------------------------------------
    // Randomization
    //--------------------------------------------------

    FPoint setRandomAngle(FPoint... exclude);
}
