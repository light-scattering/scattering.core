package eu.scattering.core.geometry.main.base.vector;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.main.base.point.IFPoint;

public interface IFVectorAdvanced {

    IFVector setSphericalCoordinates(double polar, double azimuthal);
    IFVector setRandom(IFPoint... exclude);

    IFVector relocateBase();
    IFVector relocateBase(double x, double y, double z);
    IFVector relocateBase(IFPoint base);
    IFVector relocateHead();
    IFVector relocateHead(double x, double y, double z);
    IFVector relocateHead(IFPoint head);

    IFVector add(IFVector fVector);
    IFVector sub(IFVector fVector);

    double getDimX();
    double getDimY();
    double getDimZ();

    IFVector normalize();
    IFVector reflect();
    IFVector invert();

    double getRadius();
    IFVector setRadius(double radius) throws SamePositionException;

    double getInclination();
    IFVector setInclination(double inclination);

    double getAzimuth();
    IFVector setAzimuth(double azimuth);

    double getAngle(IFPoint fPoint);
    double getAngle(IFVector fVector);

    double dProd(IFPoint fPoint);                           // Ok
    double dProd(IFVector fVector);                         // Ok

    IFVector cProd(IFPoint fPoint);                         // Ok
    IFVector cProd(IFVector fVector);                       // Ok

    boolean isParallel(IFVector fVector);
    IFVector setParallel(IFPoint base, IFPoint head);
    IFVector setParallel(IFVector fVector);

    boolean isOrthogonal(IFVector fVector);
    IFVector setOrthogonal(IFPoint headA, IFPoint headB);
    IFVector setOrthogonal(IFVector fVector);
    
    boolean isZero();

}
