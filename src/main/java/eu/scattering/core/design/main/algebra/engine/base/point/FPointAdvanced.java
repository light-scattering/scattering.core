package eu.scattering.core.design.main.algebra.engine.base.point;

import eu.scattering.core.design.main.vo.FRotor;

public interface FPointAdvanced {

    FPoint setSphericalCoordinates(double inclination, double azimuth);
    FPoint setRandomAngle(FPoint...exclude);

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    FPoint reflect();
    FPoint reflect(FPoint ref);

    FPoint normalize();

    double getLength();
    FPoint setLength(double length);
    double getInclination();
    FPoint setInclination(double inclination);
    double getAzimuth();
    FPoint setAzimuth(double azimuth);

    double getAngle(FPoint ref);
//    FPoint setAngle(FPoint ref, double angle);

//    FPoint rotate(FPoint ref, double angle);
//    double rotate(FRotor rot);

    double getDistance(FPoint ref);
    FPoint setDistance(FPoint ref, double distance);

    double getDotProduct(FPoint ref);
    FPoint setCrossProduct(FPoint ref);

//    boolean isNonDirectional();
    boolean isZero();
}
