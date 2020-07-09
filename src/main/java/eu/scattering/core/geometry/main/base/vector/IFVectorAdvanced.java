package eu.scattering.core.geometry.main.base.vector;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.main.base.point.IFPoint;

public interface IFVectorAdvanced {

    IFVector setSphericalCoordinates(double inclination, double azimuth);
    IFVector setRandom(IFPoint... exclusion);

    boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ);

//    boolean contains(IFPoint ref);

    IFVector relocateBase();                                // VAL
    IFVector relocateBase(double bX, double bY, double bZ); // VAL
    IFVector relocateBase(IFPoint base);                    // VAL
    IFVector relocateHead();                                // VAL
    IFVector relocateHead(double hX, double hY, double hZ); // VAL
    IFVector relocateHead(IFPoint head);                    // VAL

    IFVector moveForward(double distance);                  // VAL
    IFVector moveBackward(double distance);                 // VAL

    IFVector add(IFVector vector);  // VAL
    IFVector sub(IFVector vector);  // VAL

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    IFPoint getCenter();
//    IFPoint getRandom();

    IFVector normalize();               // VAL
    IFVector reflectBase();             // VAL
    IFVector reflectHead();             // VAL
    IFVector reflect(IFPoint center);   // VAL
    IFVector invertDirection();         // VAL

    double getLength();
    IFVector setLength(double length) throws SamePositionException; // VAL
    double getInclination();
    IFVector setInclination(double inclination);                    // VAL
    double getAzimuth();
    IFVector setAzimuth(double azimuth);                            // VAL

    double getAngle(IFPoint ref);
    double getAngle(IFVector ref);

    double getDotProduct(IFPoint ref);
    double getDotProduct(IFVector ref);
    IFVector getCrossProduct(IFPoint ref);      // VAL
    IFVector getCrossProduct(IFVector ref);     // VAL

    boolean isParallel(IFVector ref);
    IFVector setParallel(IFVector ref);         // VAL // tests with zero
    boolean isOrthogonal(IFVector ref);
    IFVector setOrthogonal(IFVector ref);       // VAL // tests with zero

//    boolean isAntiParallel(IFVector ref);
    
    boolean isZero();
}
