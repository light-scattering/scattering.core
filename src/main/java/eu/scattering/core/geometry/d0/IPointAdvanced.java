package eu.scattering.core.geometry.d0;

import eu.scattering.core.exception.SamePositionException;

public interface IPointAdvanced {

    IFPoint setSphericalCoordinates(double inclination, double azimuth);
    IFPoint setRandom(IFPoint ...exclude);

    IFPoint normalize();
    IFPoint reflect();

    double getRadius();
    IFPoint setRadius(double radius) throws SamePositionException;

    double getInclination();
    IFPoint setInclination(double polar);

    double getAzimuth();
    IFPoint setAzimuth(double azimuthal);

}
