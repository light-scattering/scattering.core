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

    FVector add(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector add(FVector arg);
    FVector add(FPairPos3D arg);
    FVector addSimple(double hX, double hY, double hZ);
    FVector addSimple(FPoint head);
    FVector addSimple(FPos3D head);

    FVector sub(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector sub(FVector arg);
    FVector sub(FPairPos3D arg);
    FVector subSimple(double hX, double hY, double hZ);
    FVector subSimple(FPoint head);
    FVector subSimple(FPos3D head);

    boolean isZeroLength();
    boolean isNearZeroLength();

    boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isExact(FPairPos3D arg);
    boolean isExactSimple(double hX, double hY, double hZ);
    boolean isExactSimple(FPoint head);
    boolean isExactSimple(FPos3D head);

    boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isSimilar(FPairPos3D arg);
    boolean isSimilarSimple(double hX, double hY, double hZ);
    boolean isSimilarSimple(FPoint head);
    boolean isSimilarSimple(FPos3D head);

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

    FVector normalize();

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    double getMagnitude();
    FVector setMagnitude(double magnitude);

    boolean isCollinear(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isCollinear(FVector arg);
    boolean isCollinear(FPairPos3D arg);
    boolean isCollinearSimple(double hX, double hY, double hZ);
    boolean isCollinearSimple(FPoint head);
    boolean isCollinearSimple(FPos3D head);

    FVector setCollinear(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setCollinear(FVector arg);
    FVector setCollinear(FPairPos3D arg);
    FVector setCollinearSimple(double hX, double hY, double hZ);
    FVector setCollinearSimple(FPoint head);
    FVector setCollinearSimple(FPos3D head);

    boolean isParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isParallel(FVector arg);
    boolean isParallel(FPairPos3D arg);
    boolean isParallelSimple(double hX, double hY, double hZ);
    boolean isParallelSimple(FPoint head);
    boolean isParallelSimple(FPos3D head);

    FVector setParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setParallel(FVector arg);
    FVector setParallel(FPairPos3D arg);
    FVector setParallelSimple(double hX, double hY, double hZ);
    FVector setParallelSimple(FPoint head);
    FVector setParallelSimple(FPos3D head);

    boolean isAntiParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isAntiParallel(FVector arg);
    boolean isAntiParallel(FPairPos3D arg);
    boolean isAntiParallelSimple(double hX, double hY, double hZ);
    boolean isAntiParallelSimple(FPoint head);
    boolean isAntiParallelSimple(FPos3D head);

    FVector setAntiParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setAntiParallel(FVector arg);
    FVector setAntiParallel(FPairPos3D arg);
    FVector setAntiParallelSimple(double hX, double hY, double hZ);
    FVector setAntiParallelSimple(FPoint head);
    FVector setAntiParallelSimple(FPos3D head);

    boolean isOrthogonal(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isOrthogonal(FVector arg);
    boolean isOrthogonal(FPairPos3D arg);
    boolean isOrthogonalSimple(double hX, double hY, double hZ);
    boolean isOrthogonalSimple(FPoint head);
    boolean isOrthogonalSimple(FPos3D head);

    FVector setOrthogonal(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setOrthogonal(FVector arg);
    FVector setOrthogonal(FPairPos3D arg);
    FVector setOrthogonalSimple(double hX, double hY, double hZ);
    FVector setOrthogonalSimple(FPoint head);
    FVector setOrthogonalSimple(FPos3D head);

    double getDotProduct(double bX, double bY, double bZ, double hX, double hY, double hZ);
    double getDotProduct(FVector arg);
    double getDotProduct(FPairPos3D arg);
    double getDotProductSimple(double hX, double hY, double hZ);
    double getDotProductSimple(FPoint head);
    double getDotProductSimple(FPos3D head);

    FVector setCrossProduct(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setCrossProduct(FVector arg);
    FVector setCrossProduct(FPairPos3D arg);
    FVector setCrossProductSimple(double hX, double hY, double hZ);
    FVector setCrossProductSimple(FPoint head);
    FVector setCrossProductSimple(FPos3D head);

    double getAngle(double bX, double bY, double bZ, double hX, double hY, double hZ);
    double getAngle(FVector arg);
    double getAngle(FPairPos3D arg);
    double getAngleSimple(double hX, double hY, double hZ);
    double getAngleSimple(FPoint head);
    double getAngleSimple(FPos3D head);

    FVector setAngle(double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector setAngle(FVector arg, double angle);
    FVector setAngle(FPairPos3D arg, double angle);
    FVector setAngleSimple(double hX, double hY, double hZ, double angle);
    FVector setAngleSimple(FPoint head, double angle);
    FVector setAngleSimple(FPos3D head, double angle);

    FVector rotateAround(double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector rotateAround(FVector arg, double angle);
    FVector rotateAround(FPairPos3D arg, double angle);
    FVector rotateAroundSimple(double hX, double hY, double hZ, double angle);
    FVector rotateAroundSimple(FPoint head, double angle);
    FVector rotateAroundSimple(FPos3D head, double angle);

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
