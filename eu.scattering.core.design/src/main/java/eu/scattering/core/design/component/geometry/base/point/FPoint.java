package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.annotation.Extension;
import eu.scattering.core.design.utility.annotation.Fragment;
import eu.scattering.core.design.utility.annotation.Terminator;
import eu.scattering.core.design.component.geometry.base.Base;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FPoint extends Base<FPoint> {

    FPoint set(double x, double y, double z);
    FPoint set(FPos3D pos);

    FPoint set(FPos2D posXY, double z);
    FPoint set(double x, FPos2D posYZ);

    double getX();
    FPoint setX(double x);

    double getY();
    FPoint setY(double y);

    double getZ();
    FPoint setZ(double z);

    //--------------------------------------------------

    FPos3D toFPos3D();

    //--------------------------------------------------

    FPoint add(double x, double y, double z);
    FPoint add(FPos2D xy, double z);
    FPoint add(double x, FPos2D yz);
    FPoint add(FPoint arg);
    FPoint add(FPos3D arg);

    FPoint sub(double x, double y, double z);
    FPoint sub(FPos2D xy, double z);
    FPoint sub(double x, FPos2D yz);
    FPoint sub(FPoint arg);
    FPoint sub(FPos3D arg);

    FPoint normalize();

    double getMagnitude();
    FPoint setMagnitude(double magnitude);

    boolean isZero();
    boolean isNearZero();

    boolean isExact(double x, double y, double z);
    boolean isExact(FPos3D arg);

    boolean isSimilar(double x, double y, double z);
    boolean isSimilar(FPos3D arg);

    FPoint reflectThroughCenter();
    FPoint reflect(double x, double y, double z);
    FPoint reflect(FPoint arg);
    FPoint reflect(FPos3D arg);

    double getDistance(double x, double y, double z);
    double getDistance(FPoint arg);
    double getDistance(FPos3D arg);

    FPoint setDistance(double x, double y, double z, double distance);
    FPoint setDistance(FPoint arg, double distance);
    FPoint setDistance(FPos3D arg, double distance);

    boolean isCollinear(double x, double y, double z);
    boolean isCollinear(FPoint arg);
    boolean isCollinear(FPos3D arg);

    FPoint setCollinear(double x, double y, double z);
    FPoint setCollinear(FPoint arg);
    FPoint setCollinear(FPos3D arg);

    boolean isParallel(double x, double y, double z);
    boolean isParallel(FPoint arg);
    boolean isParallel(FPos3D arg);

    FPoint setParallel(double x, double y, double z);
    FPoint setParallel(FPoint arg);
    FPoint setParallel(FPos3D arg);

    boolean isAntiParallel(double x, double y, double z);
    boolean isAntiParallel(FPoint arg);
    boolean isAntiParallel(FPos3D arg);

    FPoint setAntiParallel(double x, double y, double z);
    FPoint setAntiParallel(FPoint arg);
    FPoint setAntiParallel(FPos3D arg);

    boolean isOrthogonal(double x, double y, double z);
    boolean isOrthogonal(FPoint arg);
    boolean isOrthogonal(FPos3D arg);

    FPoint setOrthogonal(double x, double y, double z);
    FPoint setOrthogonal(FPoint arg);
    FPoint setOrthogonal(FPos3D arg);

    double getDotProduct(double x, double y, double z);
    double getDotProduct(FPoint arg);
    double getDotProduct(FPos3D arg);

    FPoint setCrossProduct(double x, double y, double z);
    FPoint setCrossProduct(FPoint arg);
    FPoint setCrossProduct(FPos3D arg);

    FPoint setSphericalCoordinates(double inclination, double azimuth);

    double getInclination();
    FPoint setInclination(double inclination);

    double getAzimuth();
    FPoint setAzimuth(double azimuth);

    double getAngle(double x, double y, double z);
    double getAngle(FPoint arg);
    double getAngle(FPos3D arg);

    //--------------------------------------------------

    FPoint setRgAngle(double x, double y, double z, double angle);
    FPoint setRgAngle(FPoint ref, double angle);
    FPoint setRgAngle(FPos3D ref, double angle);

    FPoint rotRgAround(double x, double y, double z, double angle);
    FPoint rotRgAround(FPoint ref, double angle);
    FPoint rotRgAround(FPos3D ref, double angle);

    FPoint rotate(FMatrix3x3D matrix);

    //--------------------------------------------------

    @Fragment
    double getMagnitudeP2();
    @Fragment
    double getDistanceP2(double x, double y, double z);
    @Fragment
    double getDistanceP2(FPoint arg);
    @Fragment
    double getDistanceP2(FPos3D arg);

    @Extension
    FPoint apply(Consumer<FPoint> action);

    @Terminator
    double toDouble(Function<FPoint, Double> action);
    @Terminator
    boolean toBoolean(Function<FPoint, Boolean> action);
}
