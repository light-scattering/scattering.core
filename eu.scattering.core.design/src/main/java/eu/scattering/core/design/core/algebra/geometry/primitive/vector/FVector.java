package eu.scattering.core.design.core.algebra.geometry.primitive.vector;

import eu.scattering.core.design.core.algebra.geometry.primitive.Primitive;
import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.core.data.position.FTuplePos3D;

public interface FVector extends Primitive<FVector> {

    FVector set(FPoint base, FPoint head);
//    FVector set(FPos3D base, FPos3D head);
    FVector set(FTuplePos3D position);

    FVector setBase(double bX, double bY, double bZ);
    FVector setBase(FPoint base);
//   FVector setBase(FPos3D base);

    FVector setHead(double hX, double hY, double hZ);
    FVector setHead(FPoint head);
//  FVector setHead(FPos3D head);

    double getBaseX();
    FVector setBaseX(double bX);

    double getBaseY();
    FVector setBaseY(double bY);

    double getBaseZ();
    FVector setBaseZ(double bZ);

    double getHeadX();
    FVector setHeadX(double hX);

    double getHeadY();
    FVector setHeadY(double hY);

    double getHeadZ();
    FVector setHeadZ(double hZ);

    //--------------------------------------------------

    boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ);

    boolean isZero();
    boolean isNonDirectional();

    FVector moveBase();
    FVector moveBase(double bX, double bY, double bZ);
    FVector moveBase(FPoint base);

    FVector moveHead();
    FVector moveHead(double hX, double hY, double hZ);
    FVector moveHead(FPoint head);

    FVector moveForward(double distance);
    FVector moveBackward(double distance);

    FVector add(FVector vector);
    FVector sub(FVector vector);

    double getLength();
    FVector setLength(double length);

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    FVector normalize();

    FVector reflect(FPoint center);
    FVector reflectBase();
    FVector reflectHead();
    FVector invertDirection();

    double getAngle(FPoint ref);
    double getAngle(FVector ref);
    FVector setAngle(FPoint ref, double angle);
    FVector setAngle(FVector ref, double angle);

    FVector rotate(FPoint ref, double angle);
    FVector rotate(FVector ref, double angle);

    double getDotProduct(FPoint ref);
    double getDotProduct(FVector ref);

    FVector setCrossProduct(FPoint ref);
    FVector setCrossProduct(FVector ref);

    boolean isCollinear(FVector ref);

    boolean isParallel(FVector ref);
    FVector setParallel(FVector ref);

    boolean isAntiParallel(FVector ref);
    FVector setAntiParallel(FVector ref);

    boolean isOrthogonal(FVector ref);
    FVector setOrthogonal(FVector ref);

    //--------------------------------------------------

    double getLengthP2();

    //--------------------------------------------------
    // Parsers
    //--------------------------------------------------

    FTuplePos3D toTuplePos3D();

    //--------------------------------------------------
    // Unsafe
    //--------------------------------------------------

    FVector setRef(FPoint baseRef, FPoint headRef);

    FPoint getBaseRef();
    FVector setBaseRef(FPoint baseRef);

    FPoint getHeadRef();
    FVector setHeadRef(FPoint headRef);

    //--------------------------------------------------
    // Spherical coordinates
    //--------------------------------------------------

    FVector setSphericalCoordinates(double inclination, double azimuth);

    double getInclination();
    FVector setInclination(double inclination);

    double getAzimuth();
    FVector setAzimuth(double azimuth);

    //--------------------------------------------------
    // Randomization
    //--------------------------------------------------

    FVector setRandomAngle(FPoint... exclude);
}
