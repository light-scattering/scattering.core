package eu.scattering.core.design.mutables.geometry.primitive.point;

import eu.scattering.core.design.annotations.Extension;
import eu.scattering.core.design.annotations.Facade;
import eu.scattering.core.design.annotations.Fragment;
import eu.scattering.core.design.annotations.Termination;
import eu.scattering.core.design.mutables.geometry.primitive.Primitive;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FPoint extends Primitive<FPoint> {

    FPoint set(double x, double y, double z);

    double getX();
    FPoint setX(double x);

    double getY();
    FPoint setY(double y);

    double getZ();
    FPoint setZ(double z);

    //--------------------------------------------------

    FPoint applyStateFrom(FPos3D position);

    FPos3D toFPos3D();

    //--------------------------------------------------

    FPoint add(double x, double y, double z);
    FPoint add(FPoint arg);
    FPoint add(FPos3D arg);

    FPoint sub(double x, double y, double z);
    FPoint sub(FPoint arg);
    FPoint sub(FPos3D arg);

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

    double getMagnitude();
    FPoint setMagnitude(double magnitude);

    FPoint normalize();

    double getDistance(double x, double y, double z);
    double getDistance(FPoint arg);
    double getDistance(FPos3D arg);

    FPoint setDistance(double x, double y, double z, double distance);
    FPoint setDistance(FPoint arg, double distance);
    FPoint setDistance(FPos3D arg, double distance);

    double getDotProduct(double x, double y, double z);
    double getDotProduct(FPoint arg);
    double getDotProduct(FPos3D arg);

    FPoint setCrossProduct(double x, double y, double z);
    FPoint setCrossProduct(FPoint arg);
    FPoint setCrossProduct(FPos3D arg);

    double getAngle(double x, double y, double z);
    double getAngle(FPoint arg);
    double getAngle(FPos3D arg);

    FPoint setAngle(double x, double y, double z, double angle);
    FPoint setAngle(FPoint arg, double angle);
    FPoint setAngle(FPos3D arg, double angle);

    FPoint rotAround(double x, double y, double z, double angle);
    FPoint rotAround(FPoint arg, double angle);
    FPoint rotAround(FPos3D arg, double angle);

    FPoint setSphericalCoordinates(double inclination, double azimuth);

    double getInclination();
    FPoint setInclination(double inclination);

    double getAzimuth();
    FPoint setAzimuth(double azimuth);

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

    @Facade
    FPoint applyWithFixedState(Consumer<FPoint> action);
    @Facade
    FPoint applyWithFixedMagnitude(Consumer<FPoint> action);

    @Termination
    double toDouble(Function<FPoint, Double> action);
    @Termination
    boolean toBoolean(Function<FPoint, Boolean> action);

    @Facade
    double toDoubleWithFixedState(Function<FPoint, Double> action);
    @Facade
    boolean toBooleanWithFixedState(Function<FPoint, Boolean> action);
}
