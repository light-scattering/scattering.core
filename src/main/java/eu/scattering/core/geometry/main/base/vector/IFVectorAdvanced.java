package eu.scattering.core.geometry.main.base.vector;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.main.base.point.IFPoint;

public interface IFVectorAdvanced {

    IFVector setSphericalCoordinates(double inclination, double azimuth);
    IFVector setRandom(IFPoint... exclusion);

//    boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ);
//    boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ);

    IFVector relocateBase();
    IFVector relocateBase(double bX, double bY, double bZ);
    IFVector relocateBase(IFPoint base);
    IFVector relocateHead();
    IFVector relocateHead(double hX, double hY, double hZ);
    IFVector relocateHead(IFPoint head);

//    IFVector moveForward(double distance);
//    IFVector moveBackward(double distance);

    IFVector add(IFVector vector);
    IFVector sub(IFVector vector);

    double getLengthX();
    double getLengthY();
    double getLengthZ();
//    IFPoint getCenter();

    IFVector normalize();
//    IFVector reflectBase();
    IFVector reflectHead();
//    IFVector reflect(IFPoint ref)
    IFVector invertDirection();

    double getMagnitude();
    IFVector setMagnitude(double radius) throws SamePositionException;
    double getInclination();
    IFVector setInclination(double inclination);
    double getAzimuth();
    IFVector setAzimuth(double azimuth);

    double getAngle(IFPoint ref);
    double getAngle(IFVector ref);

    double getDotProduct(IFPoint ref);
    double getDotProduct(IFVector ref);
    IFVector getCrossProduct(IFPoint ref);
    IFVector getCrossProduct(IFVector ref);

    boolean isParallel(IFVector ref);
    IFVector setParallel(IFPoint base, IFPoint head);
    IFVector setParallel(IFVector ref);
    boolean isOrthogonal(IFVector ref);
    IFVector setOrthogonal(IFPoint headA, IFPoint headB);
    IFVector setOrthogonal(IFVector ref);
    
    boolean isZero();

}
