package eu.scattering.core.design.core.algebra.geometry.primitive.point;

import eu.scattering.core.design.core.algebra.geometry.primitive.Primitive;

public interface FPoint extends Primitive<FPoint> {

    FPoint set(double x, double y, double z);
//    FPoint set(FPos3D position);

    double getX();
    FPoint setX(double x);

    double getY();
    FPoint setY(double y);

    double getZ();
    FPoint setZ(double z);

//    FPos3D toFPos3D();

    //--------------------------------------------------

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    boolean isZero();
    boolean isNonDirectional();

    FPoint normalize();

    FPoint reflect();
    FPoint reflect(FPoint ref);

    double getLength();
    FPoint setLength(double length);

    double getDistance(FPoint ref);
    FPoint setDistance(FPoint ref, double distance);

    double getAngle(FPoint ref);
    FPoint setAngle(FPoint ref, double angle);

    double getDotProduct(FPoint ref);

    FPoint setCrossProduct(FPoint ref);

    FPoint rotate(FPoint ref, double angle);

    //--------------------------------------------------

    double getLengthP2();
    double getDistanceP2(FPoint ref);

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
