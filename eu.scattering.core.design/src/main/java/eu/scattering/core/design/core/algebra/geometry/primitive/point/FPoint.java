package eu.scattering.core.design.core.algebra.geometry.primitive.point;

import eu.scattering.core.design.annotations.Radian;
import eu.scattering.core.design.annotations.SphericalCoordinates;
import eu.scattering.core.design.annotations.Utility;
import eu.scattering.core.design.core.algebra.geometry.primitive.Primitive;

public interface FPoint extends Primitive<FPoint> {

    FPoint set(double x, double y, double z);

    double getX();
    FPoint setX(double x);

    double getY();
    FPoint setY(double y);

    double getZ();
    FPoint setZ(double z);

    //--------------------------------------------------

//    FPoint set(FPos3D position);

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
    FPoint setAngle(FPoint ref, @Radian double angle);

    double getDotProduct(FPoint ref);

    FPoint setCrossProduct(FPoint ref);

    FPoint rotate(FPoint ref, @Radian double angle);

    @SphericalCoordinates
    FPoint setSphericalCoordinates(@Radian double inclination, @Radian double azimuth);

    @SphericalCoordinates
    double getInclination();
    @SphericalCoordinates
    FPoint setInclination(@Radian double inclination);

    @SphericalCoordinates
    double getAzimuth();
    @SphericalCoordinates
    FPoint setAzimuth(@Radian double azimuth);

    @Utility("Length squared")
    double getLengthP2();
    @Utility("Distance squared")
    double getDistanceP2(FPoint ref);











    //--------------------------------------------------
    // Randomization
    //--------------------------------------------------

    FPoint setRandomAngle(FPoint... exclude);
}
