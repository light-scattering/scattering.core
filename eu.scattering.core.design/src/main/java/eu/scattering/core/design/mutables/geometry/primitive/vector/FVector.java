package eu.scattering.core.design.mutables.geometry.primitive.vector;

import eu.scattering.core.design.annotations.Facade;
import eu.scattering.core.design.annotations.Fragment;
import eu.scattering.core.design.annotations.Mutation;
import eu.scattering.core.design.mutables.geometry.primitive.Primitive;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

import java.util.function.Consumer;

public interface FVector extends Primitive<FVector> {

    FVector set(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector set(FPoint base, FPoint head);

    FVector setBase(double bX, double bY, double bZ);
    FVector setBase(FPoint base);

    FVector setHead(double hX, double hY, double hZ);
    FVector setHead(FPoint head);

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

    FVector set(FPairPos3D position);

    FVector setBase(FPos3D base);
    FVector setHead(FPos3D head);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isZero();
    boolean isNonDirectional();

    boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ);

    FVector normalize();

    FVector moveBaseToCenter();
    FVector moveBase(double bX, double bY, double bZ);
    FVector moveBase(FPoint base);

    FVector moveHeadToCenter();
    FVector moveHead(double hX, double hY, double hZ);
    FVector moveHead(FPoint head);

    FVector shiftForward(double distance);
    FVector shiftBackward(double distance);

    FVector add(FVector vector);
    FVector sub(FVector vector);

    FVector reflect(FPoint center);
    FVector reflectBase();
    FVector reflectHead();
    FVector invertDirection();

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    double getLength();
    FVector setLength(double length);

    double getAngle(FVector ref);
//    double setAngle(FVector ref, double angle); // The Rodrigues rotation formula.

    boolean isCollinear(FVector ref);
//    FVector setCollinear(FVector ref); // To the lowest angle.

    boolean isParallel(FVector ref);
    FVector setParallel(FVector ref);

    boolean isAntiParallel(FVector ref);
    FVector setAntiParallel(FVector ref);

    boolean isOrthogonal(FVector ref);
    FVector setOrthogonal(FVector ref);

    double getDotProduct(FVector ref);

    FVector setCrossProduct(FVector ref);

    FVector setSphericalCoordinates(double inclination, double azimuth);

    double getInclination();
    FVector setInclination(double inclination);

    double getAzimuth();
    FVector setAzimuth(double azimuth);

    //--------------------------------------------------

    @Mutation
    FVector setRef(FPoint refBase, FPoint refHead);

    @Mutation
    FPoint getRefBase();
    @Mutation
    FVector setRefBase(FPoint refBase);

    @Mutation
    FPoint getRefHead();
    @Mutation
    FVector setRefHead(FPoint refHead);

    //--------------------------------------------------

//    @Extension
//    FVector apply(Consumer<FVector> action);

    @Facade
    FVector mutateAtCenter(Consumer<FVector> action);
//    FVector mutateHeadAtCenter(Consumer<FPoint> action);

    //--------------------------------------------------

    @Fragment
    double getLengthP2();
}
