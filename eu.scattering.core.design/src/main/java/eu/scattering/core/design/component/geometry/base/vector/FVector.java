package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.component.geometry.base.Base;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.util.annotation.Extension;
import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.design.util.annotation.Terminator;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FVector extends Base<FVector> {

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
    FVector add(FPoint base, FPoint head);
    FVector add(FPos3D base, FPos3D head);
    FVector add(FVector arg);
    FVector add(FPairPos3D arg);
    FVector addBaseCommon(double hX, double hY, double hZ);
    FVector addBaseCommon(FPoint head);
    FVector addBaseCommon(FPos3D head);
    FVector addBaseZero(double hX, double hY, double hZ);
    FVector addBaseZero(FPoint head);
    FVector addBaseZero(FPos3D head);

    FVector sub(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector sub(FPoint base, FPoint head);
    FVector sub(FPos3D base, FPos3D head);
    FVector sub(FVector arg);
    FVector sub(FPairPos3D arg);
    FVector subBaseCommon(double hX, double hY, double hZ);
    FVector subBaseCommon(FPoint head);
    FVector subBaseCommon(FPos3D head);
    FVector subBaseZero(double hX, double hY, double hZ);
    FVector subBaseZero(FPoint head);
    FVector subBaseZero(FPos3D head);

    FVector normalize();

    double getMagnitude();
    FVector setMagnitude(double magnitude);

    boolean isZeroLength();
    boolean isNearZeroLength();

    boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isExact(FPoint base, FPoint head);
    boolean isExact(FPos3D base, FPos3D head);
    boolean isExact(FPairPos3D arg);
    boolean isExactBaseCommon(double hX, double hY, double hZ);
    boolean isExactBaseCommon(FPoint head);
    boolean isExactBaseCommon(FPos3D head);
    boolean isExactBaseZero(double hX, double hY, double hZ);
    boolean isExactBaseZero(FPoint head);
    boolean isExactBaseZero(FPos3D head);

    boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isSimilar(FPoint base, FPoint head);
    boolean isSimilar(FPos3D base, FPos3D head);
    boolean isSimilar(FPairPos3D arg);
    boolean isSimilarBaseCommon(double hX, double hY, double hZ);
    boolean isSimilarBaseCommon(FPoint head);
    boolean isSimilarBaseCommon(FPos3D head);
    boolean isSimilarBaseZero(double hX, double hY, double hZ);
    boolean isSimilarBaseZero(FPoint head);
    boolean isSimilarBaseZero(FPos3D head);

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
    boolean isCollinear(FPoint base, FPoint head);
    boolean isCollinear(FPos3D base, FPos3D head);
    boolean isCollinear(FVector arg);
    boolean isCollinear(FPairPos3D arg);
    boolean isCollinearBaseCommon(double hX, double hY, double hZ);
    boolean isCollinearBaseCommon(FPoint head);
    boolean isCollinearBaseCommon(FPos3D head);
    boolean isCollinearBaseZero(double hX, double hY, double hZ);
    boolean isCollinearBaseZero(FPoint head);
    boolean isCollinearBaseZero(FPos3D head);

    FVector setCollinear(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setCollinear(FPoint base, FPoint head);
    FVector setCollinear(FPos3D base, FPos3D head);
    FVector setCollinear(FVector arg);
    FVector setCollinear(FPairPos3D arg);
    FVector setCollinearBaseCommon(double hX, double hY, double hZ);
    FVector setCollinearBaseCommon(FPoint head);
    FVector setCollinearBaseCommon(FPos3D head);
    FVector setCollinearBaseZero(double hX, double hY, double hZ);
    FVector setCollinearBaseZero(FPoint head);
    FVector setCollinearBaseZero(FPos3D head);

    boolean isParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isParallel(FPoint base, FPoint head);
    boolean isParallel(FPos3D base, FPos3D head);
    boolean isParallel(FVector arg);
    boolean isParallel(FPairPos3D arg);
    boolean isParallelBaseCommon(double hX, double hY, double hZ);
    boolean isParallelBaseCommon(FPoint head);
    boolean isParallelBaseCommon(FPos3D head);
    boolean isParallelBaseZero(double hX, double hY, double hZ);
    boolean isParallelBaseZero(FPoint head);
    boolean isParallelBaseZero(FPos3D head);

    FVector setParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setParallel(FPoint base, FPoint head);
    FVector setParallel(FPos3D base, FPos3D head);
    FVector setParallel(FVector arg);
    FVector setParallel(FPairPos3D arg);
    FVector setParallelBaseCommon(double hX, double hY, double hZ);
    FVector setParallelBaseCommon(FPoint head);
    FVector setParallelBaseCommon(FPos3D head);
    FVector setParallelBaseZero(double hX, double hY, double hZ);
    FVector setParallelBaseZero(FPoint head);
    FVector setParallelBaseZero(FPos3D head);

    boolean isAntiParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isAntiParallel(FPoint base, FPoint head);
    boolean isAntiParallel(FPos3D base, FPos3D head);
    boolean isAntiParallel(FVector arg);
    boolean isAntiParallel(FPairPos3D arg);
    boolean isAntiParallelBaseCommon(double hX, double hY, double hZ);
    boolean isAntiParallelBaseCommon(FPoint head);
    boolean isAntiParallelBaseCommon(FPos3D head);
    boolean isAntiParallelBaseZero(double hX, double hY, double hZ);
    boolean isAntiParallelBaseZero(FPoint head);
    boolean isAntiParallelBaseZero(FPos3D head);

    FVector setAntiParallel(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setAntiParallel(FPoint base, FPoint head);
    FVector setAntiParallel(FPos3D base, FPos3D head);
    FVector setAntiParallel(FVector arg);
    FVector setAntiParallel(FPairPos3D arg);
    FVector setAntiParallelBaseCommon(double hX, double hY, double hZ);
    FVector setAntiParallelBaseCommon(FPoint head);
    FVector setAntiParallelBaseCommon(FPos3D head);
    FVector setAntiParallelBaseZero(double hX, double hY, double hZ);
    FVector setAntiParallelBaseZero(FPoint head);
    FVector setAntiParallelBaseZero(FPos3D head);

    boolean isOrthogonal(double bX, double bY, double bZ, double hX, double hY, double hZ);
    boolean isOrthogonal(FPoint base, FPoint head);
    boolean isOrthogonal(FPos3D base, FPos3D head);
    boolean isOrthogonal(FVector arg);
    boolean isOrthogonal(FPairPos3D arg);
    boolean isOrthogonalBaseCommon(double hX, double hY, double hZ);
    boolean isOrthogonalBaseCommon(FPoint head);
    boolean isOrthogonalBaseCommon(FPos3D head);
    boolean isOrthogonalBaseZero(double hX, double hY, double hZ);
    boolean isOrthogonalBaseZero(FPoint head);
    boolean isOrthogonalBaseZero(FPos3D head);

    FVector setOrthogonal(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setOrthogonal(FPoint base, FPoint head);
    FVector setOrthogonal(FPos3D base, FPos3D head);
    FVector setOrthogonal(FVector arg);
    FVector setOrthogonal(FPairPos3D arg);
    FVector setOrthogonalBaseCommon(double hX, double hY, double hZ);
    FVector setOrthogonalBaseCommon(FPoint head);
    FVector setOrthogonalBaseCommon(FPos3D head);
    FVector setOrthogonalBaseZero(double hX, double hY, double hZ);
    FVector setOrthogonalBaseZero(FPoint head);
    FVector setOrthogonalBaseZero(FPos3D head);

    double getDotProduct(double bX, double bY, double bZ, double hX, double hY, double hZ);
    double getDotProduct(FPoint base, FPoint head);
    double getDotProduct(FPos3D base, FPos3D head);
    double getDotProduct(FVector arg);
    double getDotProduct(FPairPos3D arg);
    double getDotProductBaseCommon(double hX, double hY, double hZ);
    double getDotProductBaseCommon(FPoint head);
    double getDotProductBaseCommon(FPos3D head);
    double getDotProductBaseZero(double hX, double hY, double hZ);
    double getDotProductBaseZero(FPoint head);
    double getDotProductBaseZero(FPos3D head);

    FVector setCrossProduct(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FVector setCrossProduct(FPoint base, FPoint head);
    FVector setCrossProduct(FPos3D base, FPos3D head);
    FVector setCrossProduct(FVector arg);
    FVector setCrossProduct(FPairPos3D arg);
    FVector setCrossProductBaseCommon(double hX, double hY, double hZ);
    FVector setCrossProductBaseCommon(FPoint head);
    FVector setCrossProductBaseCommon(FPos3D head);
    FVector setCrossProductBaseZero(double hX, double hY, double hZ);
    FVector setCrossProductBaseZero(FPoint head);
    FVector setCrossProductBaseZero(FPos3D head);

    double getAngle(double bX, double bY, double bZ, double hX, double hY, double hZ);
    double getAngle(FPoint base, FPoint head);
    double getAngle(FPos3D base, FPos3D head);
    double getAngle(FVector arg);
    double getAngle(FPairPos3D arg);
    double getAngleBaseCommon(double hX, double hY, double hZ);
    double getAngleBaseCommon(FPoint head);
    double getAngleBaseCommon(FPos3D head);
    double getAngleBaseZero(double hX, double hY, double hZ);
    double getAngleBaseZero(FPoint head);
    double getAngleBaseZero(FPos3D head);

    FVector setSphericalCoordinates(double inclination, double azimuth);

    double getInclination();
    FVector setInclination(double inclination);

    double getAzimuth();
    FVector setAzimuth(double azimuth);

    //--------------------------------------------------

    @Modificator
    FVector setRef(FPoint refBase, FPoint refHead);

    @Modificator
    FPoint getRefBase();
    @Modificator
    FVector setRefBase(FPoint refBase);

    @Modificator
    FPoint getRefHead();
    @Modificator
    FVector setRefHead(FPoint refHead);

    //--------------------------------------------------

    @Fragment
    double getMagnitudeP2();

    @Extension
    FVector apply(Consumer<FVector> action);

    @Terminator
    double toDouble(Function<FVector, Double> action);
    @Terminator
    boolean toBoolean(Function<FVector, Boolean> action);
}
