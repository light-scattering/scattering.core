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

    double getLengthX();
    double getLengthY();
    double getLengthZ();

    //--------------------------------------------------

    FVector applyStateFrom(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    FVector add(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector add(FVector arg);
    FVector add(FPairPos3D arg);
    FVector addCompact(double hX, double hY, double hZ);
    FVector addCompact(FPoint head);
    FVector addCompact(FPos3D head);

    FVector sub(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector sub(FVector arg);
    FVector sub(FPairPos3D arg);
    FVector subCompact(double hX, double hY, double hZ);
    FVector subCompact(FPoint head);
    FVector subCompact(FPos3D head);

    FVector normalize();

    double getMagnitude();
    FVector setMagnitude(double magnitude);

    boolean isZeroLength();
    boolean isNearZeroLength();

    boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isExact(FPairPos3D arg);
    boolean isExactCompact(double hX, double hY, double hZ);
    boolean isExactCompact(FPoint head);
    boolean isExactCompact(FPos3D head);

    boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isSimilar(FPairPos3D arg);
    boolean isSimilarCompact(double hX, double hY, double hZ);
    boolean isSimilarCompact(FPoint head);
    boolean isSimilarCompact(FPos3D head);

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

    boolean isCollinear(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isCollinear(FVector arg);
    boolean isCollinear(FPairPos3D arg);
    boolean isCollinearCompact(double hX, double hY, double hZ);
    boolean isCollinearCompact(FPoint head);
    boolean isCollinearCompact(FPos3D head);

    FVector setCollinear(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setCollinear(FVector arg);
    FVector setCollinear(FPairPos3D arg);
    FVector setCollinearCompact(double hX, double hY, double hZ);
    FVector setCollinearCompact(FPoint head);
    FVector setCollinearCompact(FPos3D head);

    boolean isParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isParallel(FVector arg);
    boolean isParallel(FPairPos3D arg);
    boolean isParallelCompact(double hX, double hY, double hZ);
    boolean isParallelCompact(FPoint head);
    boolean isParallelCompact(FPos3D head);

    FVector setParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setParallel(FVector arg);
    FVector setParallel(FPairPos3D arg);
    FVector setParallelCompact(double hX, double hY, double hZ);
    FVector setParallelCompact(FPoint head);
    FVector setParallelCompact(FPos3D head);

    boolean isAntiParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isAntiParallel(FVector arg);
    boolean isAntiParallel(FPairPos3D arg);
    boolean isAntiParallelCompact(double hX, double hY, double hZ);
    boolean isAntiParallelCompact(FPoint head);
    boolean isAntiParallelCompact(FPos3D head);

    FVector setAntiParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setAntiParallel(FVector arg);
    FVector setAntiParallel(FPairPos3D arg);
    FVector setAntiParallelCompact(double hX, double hY, double hZ);
    FVector setAntiParallelCompact(FPoint head);
    FVector setAntiParallelCompact(FPos3D head);

    boolean isOrthogonal(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isOrthogonal(FVector arg);
    boolean isOrthogonal(FPairPos3D arg);
    boolean isOrthogonalCompact(double hX, double hY, double hZ);
    boolean isOrthogonalCompact(FPoint head);
    boolean isOrthogonalCompact(FPos3D head);

    FVector setOrthogonal(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setOrthogonal(FVector arg);
    FVector setOrthogonal(FPairPos3D arg);
    FVector setOrthogonalCompact(double hX, double hY, double hZ);
    FVector setOrthogonalCompact(FPoint head);
    FVector setOrthogonalCompact(FPos3D head);

    double getDotProduct(double bX, double bY, double bZ, double hX, double hY, double hZ);
    double getDotProduct(FVector arg);
    double getDotProduct(FPairPos3D arg);
    double getDotProductCompact(double hX, double hY, double hZ);
    double getDotProductCompact(FPoint head);
    double getDotProductCompact(FPos3D head);

    FVector setCrossProduct(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setCrossProduct(FVector arg);
    FVector setCrossProduct(FPairPos3D arg);
    FVector setCrossProductCompact(double hX, double hY, double hZ);
    FVector setCrossProductCompact(FPoint head);
    FVector setCrossProductCompact(FPos3D head);

    FVector setSphericalCoordinates(double inclination, double azimuth);

    double getInclination();
    FVector setInclination(double inclination);

    double getAzimuth();
    FVector setAzimuth(double azimuth);

    double getAngle(double bX, double bY, double bZ, double hX, double hY, double hZ);
    double getAngle(FVector arg);
    double getAngle(FPairPos3D arg);
    double getAngleCompact(double hX, double hY, double hZ);
    double getAngleCompact(FPoint head);
    double getAngleCompact(FPos3D head);

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

    @Termination
    double toDouble(Function<FVector, Double> action);
    @Termination
    boolean toBoolean(Function<FVector, Boolean> action);
}
