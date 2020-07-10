package eu.scattering.core.geometry.main.base.point;

import eu.scattering.core.exception.SamePositionException;

public interface IFPointAdvanced {

    IFPoint setSphericalCoordinates(double inclination, double azimuth);    // VAL
    IFPoint setRandom(IFPoint ...exclude);  // VAL

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    IFPoint normalize();    // VAL
    IFPoint reflect();      // VAL
    IFPoint reflect(IFPoint ref);   // VAL

    double getLength();
    IFPoint setLength(double length) throws SamePositionException, IllegalArgumentException;    // Val
    double getInclination();
    IFPoint setInclination(double inclination); // val
    double getAzimuth();
    IFPoint setAzimuth(double azimuth); // VAL

    double getAngle(IFPoint ref);

    double getDistance(IFPoint ref);
    IFPoint setDistance(IFPoint ref, double distance) throws SamePositionException, IllegalArgumentException;   // VAL

    double getDotProduct(IFPoint ref);
    IFPoint getCrossProduct(IFPoint ref);   // VAL

    boolean isZero();
}
