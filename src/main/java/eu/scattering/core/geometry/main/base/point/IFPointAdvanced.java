package eu.scattering.core.geometry.main.base.point;

import eu.scattering.core.exception.PositionException;

public interface IFPointAdvanced {

    IFPoint setSphericalCoordinates(double inclination, double azimuth);    // VAL
    IFPoint setRandomAngle(IFPoint ...exclude);  // VAL

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    IFPoint reflect();      // VAL
    IFPoint reflect(IFPoint ref);   // VAL

    IFPoint normalize();    // VAL

    double getLength();
    IFPoint setLength(double length) throws PositionException, IllegalArgumentException;    // Val
    double getInclination();
    IFPoint setInclination(double inclination); // val
    double getAzimuth();
    IFPoint setAzimuth(double azimuth); // VAL

    double getAngle(IFPoint ref);
//    IFPoint setAngle(IFPoint ref, double angle);

    double getDistance(IFPoint ref);
    IFPoint setDistance(IFPoint ref, double distance) throws PositionException, IllegalArgumentException;   // VAL

    double getDotProduct(IFPoint ref);
    IFPoint setCrossProduct(IFPoint ref);   // VAL

    boolean isZero();
}
