package eu.scattering.core.design.mutables.geometry.primitive.vector;

import eu.scattering.core.design.annotations.*;
import eu.scattering.core.design.mutables.geometry.primitive.Primitive;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FVector extends Primitive<FVector> {

    FVector set(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector set(FPoint base, FPoint head);
    FVector set(FPos3D base, FPos3D head);

    FVector setBase(double bX, double bY, double bZ);
    FVector setBase(FPoint base);
    FVector setBase(FPos3D base);

    FVector setHead(double hX, double hY, double hZ);
    FVector setHead(FPoint head);
    FVector setHead(FPos3D head);

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

    FVector applyStateFrom(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isZeroLength();
    boolean isNearZeroLength();

    boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isExact(FPairPos3D arg);

    boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isSimilar(FPairPos3D arg);

    FVector normalize();

    FVector moveBaseToCenter();
    FVector moveBase(double bX, double bY, double bZ);
    FVector moveBase(FPoint base);
    FVector moveBase(FPos3D base);

    FVector moveHeadToCenter();
    FVector moveHead(double hX, double hY, double hZ);
    FVector moveHead(FPoint head);
    FVector moveHead(FPos3D head);

    FVector reflectThroughCenter();
    FVector reflect(double x, double y, double z);
    FVector reflect(FPoint arg);
    FVector reflect(FPos3D arg);

    FVector reflectBase();
    FVector reflectHead();

    FVector swapBaseWithHead();

    FVector shiftForward(double distance);
    FVector shiftBackward(double distance);

    // TODO - Might be confusing
    FVector add(FVector arg);
    FVector add(FPairPos3D arg);

    FVector sub(FVector arg);
    FVector sub(FPairPos3D arg);

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    double getMagnitude();
    FVector setMagnitude(double magnitude);

    double getAngle(FVector arg);
    double getAngle(FPairPos3D arg);

    FVector setAngle(FVector arg, double angle);
    FVector setAngle(FPairPos3D arg, double angle);

    boolean isCollinear(FVector arg);
    boolean isCollinear(FPairPos3D arg);

    FVector setCollinear(FVector arg);
    FVector setCollinear(FPairPos3D arg);

    boolean isParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isParallel(FVector arg);
    boolean isParallel(FPairPos3D arg);
    boolean isParallel(double hX, double hY, double hZ);
    boolean isParallel(FPoint arg);
    boolean isParallel(FPos3D arg);

    FVector setParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setParallel(FVector arg);
    FVector setParallel(FPairPos3D arg);
    FVector setParallel(double hX, double hY, double hZ);
    FVector setParallel(FPoint head);
    FVector setParallel(FPos3D head);

    boolean isAntiParallel(FVector arg);
    boolean isAntiParallel(FPairPos3D arg);

    FVector setAntiParallel(FVector arg);
    FVector setAntiParallel(FPairPos3D arg);

    boolean isOrthogonal(FVector arg);
//    boolean isOrthogonal(FPairPos3D arg);

    FVector setOrthogonal(FVector arg);
//    FVector setOrthogonal(FPairPos3D arg);

    double getDotProduct(FVector arg);
//    double getDotProduct(FPairPos3D arg);

    FVector setCrossProduct(FVector arg);
//    FVector setCrossProduct(FPairPos3D arg);

    FVector rotateAround(FVector arg, double angle);
//    FVector rotateAround(FPairPos3D arg, double angle);

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
    double getMagnitudeP2();

    @Extension
    FVector apply(Consumer<FVector> action);

    @Facade
    FVector applyWithFixedState(Consumer<FVector> action);
    @Facade
    FVector applyWithFixedMagnitude(Consumer<FVector> action);
    @Facade
    FVector applyWithCenteredPosition(Consumer<FVector> action);

    @Termination
    double toDouble(Function<FVector, Double> action);
    @Termination
    boolean toBoolean(Function<FVector, Boolean> action);

    @Facade
    double toDoubleWithFixedState(Function<FVector, Double> action);
    @Facade
    boolean toBooleanWithFixedState(Function<FVector, Boolean> action);
}
