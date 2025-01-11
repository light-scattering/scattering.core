package eu.scattering.core.design.mutables.geometry.primitive.vector;

import eu.scattering.core.design.annotations.Facade;
import eu.scattering.core.design.annotations.Fragment;
import eu.scattering.core.design.annotations.Mutation;
import eu.scattering.core.design.annotations.Termination;
import eu.scattering.core.design.mutables.geometry.primitive.Primitive;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

import java.util.function.Consumer;
import java.util.function.Function;

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

    boolean isZeroLength();
    boolean isNearZeroLength();

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

    FVector add(FVector op);
    FVector sub(FVector op);

    FVector reflect(FPoint op);
    FVector reflectBase();
    FVector reflectHead();
    FVector invertDirection();

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    double getLength();
    FVector setLength(double length);

    double getAngle(FVector op);
//    double setAngle(FVector ref, double angle); // The Rodrigues rotation formula.

    boolean isCollinear(FVector op);
//    FVector setCollinear(FVector ref); // To the lowest angle.

    boolean isParallel(FVector op);
    FVector setParallel(FVector op);

    boolean isAntiParallel(FVector op);
    FVector setAntiParallel(FVector op);

    boolean isOrthogonal(FVector op);
    FVector setOrthogonal(FVector op);

    double getDotProduct(FVector op);
    FVector setCrossProduct(FVector op);

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

    @Fragment
    double getLengthP2();

//    @Extension
//    FVector apply(Consumer<FVector> action);

    @Facade
    FVector applyWithFixedState(Consumer<FVector> action);
    @Facade
    FVector applyWithFixedLength(Consumer<FVector> action);
    @Facade
    FVector applyWithPositionZero(Consumer<FVector> action);

//    @Termination
//    double toDouble(Function<FVector, Double> action);
//    @Termination
//    boolean toBoolean(Function<FVector, Boolean> action);
}
