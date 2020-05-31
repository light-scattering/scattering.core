package eu.scattering.core.geometry.d1.fvector;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.d0.IFPoint;

public interface IFVectorAdvanced {

    IFVector setSphericalCoordinates(double polar, double azimuthal, double radius);
    IFVector randomizeOnSphere(double radius);
    IFVector normalize();
    IFVector reflect();

    double getPolarAngle();
    IFVector setPolarAngle(double polar);

    double getAzimuthalAngle();
    IFVector setAzimuthalAngle(double azimuthal);

    double getRadius();
    IFVector setRadius(double distance) throws SamePositionException;


}
