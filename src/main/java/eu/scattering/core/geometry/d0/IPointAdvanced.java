package eu.scattering.core.geometry.d0;

import eu.scattering.core.exception.SamePositionException;

public interface IPointAdvanced {

    IFPoint setSphericalCoordinates(double polar, double azimuthal, double radius);
    IFPoint randomize(double radius);
    IFPoint normalize();
    IFPoint reflect();

    double getPolarAngle();
    IFPoint setPolarAngle(double polar);

    double getAzimuthalAngle();
    IFPoint setAzimuthalAngle(double azimuthal);

    double getRadius();
    IFPoint setRadius(double distance) throws SamePositionException;

}
