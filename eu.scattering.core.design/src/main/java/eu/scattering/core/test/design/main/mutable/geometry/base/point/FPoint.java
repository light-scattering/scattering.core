package eu.scattering.core.test.design.main.mutable.geometry.base.point;

import eu.scattering.core.test.design.main.mutable.geometry.base.Base;
import eu.scattering.core.test.design.main.mutable.Mutable;
import eu.scattering.core.test.design.development.Development;

public interface FPoint extends Mutable<FPoint>, Base<FPoint>, Development<FPoint> {

    FPoint set(double x, double y, double z);

    double getX();
    FPoint setX(double x);

    double getY();
    FPoint setY(double y);

    double getZ();
    FPoint setZ(double z);

    FPoint setSphericalCoordinates(double inclination, double azimuth);
    FPoint setRandomAngle(FPoint...exclude);

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    FPoint reflect();
    FPoint reflect(FPoint ref);

    FPoint normalize();

    double getLength();
    double getLengthP2();
    FPoint setLength(double length);
    double getInclination();
    FPoint setInclination(double inclination);
    double getAzimuth();
    FPoint setAzimuth(double azimuth);

    double getAngle(FPoint ref);
    FPoint setAngle(FPoint ref, double angle);

    FPoint rotate(FPoint ref, double angle);

    double getDistance(FPoint ref);
    double getDistanceP2(FPoint ref);
    FPoint setDistance(FPoint ref, double distance);

    double getDotProduct(FPoint ref);
    FPoint setCrossProduct(FPoint ref);

    boolean isNonDirectional();
    boolean isZero();
}
