package eu.scattering.core.geometry.base.vector;

import eu.scattering.core.exception.DirectionException;
import eu.scattering.core.exception.PositionException;
import eu.scattering.core.geometry.base.point.IFPoint;

public interface IFVectorAdvanced {

    IFVector setSphericalCoordinates(double inclination, double azimuth);
    IFVector setRandomAngle(IFPoint... exclusion);

    boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ);

    IFVector moveBase();
    IFVector moveBase(double bX, double bY, double bZ);
    IFVector moveBase(IFPoint base);

    IFVector moveHead();
    IFVector moveHead(double hX, double hY, double hZ);
    IFVector moveHead(IFPoint head);

    IFVector moveForward(double distance) throws DirectionException;
    IFVector moveBackward(double distance) throws DirectionException;

    IFVector add(IFVector vector);
    IFVector sub(IFVector vector);

    double getLength();
    IFVector setLength(double length) throws DirectionException;

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    IFVector normalize() throws DirectionException;

    IFVector reflect(IFPoint center);
    IFVector reflectBase();
    IFVector reflectHead();
    IFVector invertDirection();

    double getInclination();
    IFVector setInclination(double inclination);
    double getAzimuth();
    IFVector setAzimuth(double azimuth);

    double getAngle(IFPoint ref) throws PositionException, DirectionException;
    double getAngle(IFVector ref) throws DirectionException;

    double getDotProduct(IFPoint ref);
    double getDotProduct(IFVector ref);
    IFVector setCrossProduct(IFPoint ref);
    IFVector setCrossProduct(IFVector ref);

    boolean isParallel(IFVector ref) throws DirectionException;
    IFVector setParallel(IFVector ref) throws DirectionException;
    boolean isAntiParallel(IFVector ref) throws DirectionException;
    IFVector setAntiParallel(IFVector ref) throws DirectionException;

    boolean isOrthogonal(IFVector ref) throws DirectionException;
    IFVector setOrthogonal(IFVector ref) throws PositionException, DirectionException;
    
    boolean isNonDirectional();
}
