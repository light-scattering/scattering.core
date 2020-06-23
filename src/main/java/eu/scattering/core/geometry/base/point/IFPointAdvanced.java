package eu.scattering.core.geometry.base.point;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.base.vector.IFVector;

public interface IFPointAdvanced {

    IFPoint setSphericalCoordinates(double inclination, double azimuth);
    IFPoint setRandom(IFPoint ...exclude);

    IFPoint normalize();

    IFPoint reflect();
    IFPoint reflect(IFPoint fPoint);

    double getRadius();
    IFPoint setRadius(double radius) throws SamePositionException, IllegalArgumentException;

    double getInclination();
    IFPoint setInclination(double inclination);

    double getAzimuth();
    IFPoint setAzimuth(double azimuth);

    double getAngle(IFPoint fPoint);

    double getDistance(IFPoint fPoint);
    IFPoint setDistance(IFPoint fPoint, double distance) throws SamePositionException, IllegalArgumentException;

    double dProd(IFPoint fPoint);
    IFPoint cProd(IFPoint fPoint);

    boolean isZero();

}
