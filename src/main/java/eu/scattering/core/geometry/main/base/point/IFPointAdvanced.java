package eu.scattering.core.geometry.main.base.point;

import eu.scattering.core.exception.SamePositionException;

public interface IFPointAdvanced {

    IFPoint setSphericalCoordinates(double inclination, double azimuth);
    IFPoint setRandom(IFPoint ...exclude);

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    IFPoint normalize();
    IFPoint reflect();
    IFPoint reflect(IFPoint ref);

    double getRadius();
    IFPoint setRadius(double radius) throws SamePositionException, IllegalArgumentException;
    double getInclination();
    IFPoint setInclination(double inclination);
    double getAzimuth();
    IFPoint setAzimuth(double azimuth);

    double getAngle(IFPoint ref);

    double getDistance(IFPoint ref);
    IFPoint setDistance(IFPoint ref, double distance) throws SamePositionException, IllegalArgumentException;

    double getDotProduct(IFPoint ref);
    IFPoint getCrossProduct(IFPoint ref);

    boolean isZero();

}
