package eu.scattering.core.geometry.base.vector;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.base.point.IFPoint;

public interface IFVectorAdvanced {

    IFVector setSphericalCoordinates(double polar, double azimuthal);
    IFVector setRandom(IFPoint...exclude);

    IFVector relocateBase(double x, double y, double z);
    IFVector relocateBase(IFPoint base);
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

    double getAngle(IFVector fVector);

    double dProd(IFVector fVector);
    IFVector cProd(IFVector fVector);

    boolean isParallel(IFVector fVector);
    boolean isOrthogonal(IFVector fVector);
    
    boolean isZero();

}
