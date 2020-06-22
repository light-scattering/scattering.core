package eu.scattering.core.geometry.base.point;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.base.vector.IFVector;

public interface IFPointAdvanced {

    IFPoint setSphericalCoordinates(double inclination, double azimuth);
    IFPoint setRandom(IFPoint ...exclude);

    IFPoint normalize();
    IFPoint reflect();

    double getRadius();
    IFPoint setRadius(double radius) throws SamePositionException;

    double getInclination();
    IFPoint setInclination(double inclination);

    double getAzimuth();
    IFPoint setAzimuth(double azimuth);

    double dProd(IFPoint fPoint);
    IFPoint cProd(IFPoint fPoint);

    boolean isZero();

}
