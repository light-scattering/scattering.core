package eu.scattering.core.design.engine.base.point;

public interface FPointAdvanced {

    FPoint setSphericalCoordinates(double inclination, double azimuth);
    FPoint setRandomAngle(FPoint...exclude);

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    FPoint reflect();
    FPoint reflect(FPoint ref);

    FPoint normalize() throws IllegalStateException;

    double getLength();
    FPoint setLength(double length) throws IllegalStateException;
    double getInclination();
    FPoint setInclination(double inclination);
    double getAzimuth();
    FPoint setAzimuth(double azimuth);

    double getAngle(FPoint ref) throws IllegalStateException;

    double getDistance(FPoint ref);
    FPoint setDistance(FPoint ref, double distance) throws IllegalStateException;

    double getDotProduct(FPoint ref);
    FPoint setCrossProduct(FPoint ref);

    boolean isZero();
}
