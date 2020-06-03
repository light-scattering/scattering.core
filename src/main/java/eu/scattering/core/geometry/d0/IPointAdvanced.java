package eu.scattering.core.geometry.d0;

import eu.scattering.core.exception.SamePositionException;

public interface IPointAdvanced {

    IFPoint setSphere(double polar, double azimuthal);
    IFPoint setRandom(IFPoint ...exclude);

    IFPoint normalize();
    IFPoint reflect();

    double getInclination();
    IFPoint setInclination(double polar);

    double getAzimuth();
    IFPoint setAzimuth(double azimuthal);

    double getRadius();
    IFPoint setRadius(double radius) throws SamePositionException;

}
