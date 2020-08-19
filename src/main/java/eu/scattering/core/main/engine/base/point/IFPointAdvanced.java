package eu.scattering.core.main.engine.base.point;

import eu.scattering.core.support.exception.DirectionException;

public interface IFPointAdvanced {

    IFPoint setSphericalCoordinates(double inclination, double azimuth);
    IFPoint setRandomAngle(IFPoint ...exclude);

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    IFPoint reflect();
    IFPoint reflect(IFPoint ref);

    IFPoint normalize() throws DirectionException;

    double getLength();
    IFPoint setLength(double length) throws DirectionException;
    double getInclination();
    IFPoint setInclination(double inclination);
    double getAzimuth();
    IFPoint setAzimuth(double azimuth);

    double getAngle(IFPoint ref) throws DirectionException;

    double getDistance(IFPoint ref);
    IFPoint setDistance(IFPoint ref, double distance) throws DirectionException;

    double getDotProduct(IFPoint ref);
    IFPoint setCrossProduct(IFPoint ref);

    boolean isZero();
}
