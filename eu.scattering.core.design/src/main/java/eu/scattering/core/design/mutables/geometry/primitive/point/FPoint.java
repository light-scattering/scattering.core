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

    FPoint set(FPos3D position);

    FPos3D toFPos3D();

    //--------------------------------------------------

    boolean isZero();
    boolean isNearZero();

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    FPoint normalize();

    FPoint reflect();
    FPoint reflect(FPoint op);

    double getLength();
    FPoint setLength(double length);

    double getDistance(FPoint op);
    FPoint setDistance(FPoint op, double distance);

    double getAngle(FPoint op);
    FPoint setAngle(FPoint op, double angle);

    double getDotProduct(FPoint op);
    FPoint setCrossProduct(FPoint op);

    FPoint setSphericalCoordinates(double inclination, double azimuth);

    double getInclination();
    FPoint setInclination(double inclination);

    double getAzimuth();
    FPoint setAzimuth(double azimuth);

    //--------------------------------------------------

    @Fragment
    double getLengthP2();
    @Fragment
    double getDistanceP2(FPoint op);

    @Extension
    FPoint apply(Consumer<FPoint> action);

    @Facade
    FPoint applyWithFixedState(Consumer<FPoint> action);
    @Facade
    FPoint applyWithFixedLength(Consumer<FPoint> action);

    @Termination
    double toDouble(Function<FPoint, Double> action);
    @Termination
    boolean toBoolean(Function<FPoint, Boolean> action);

    @Facade
    double toDoubleWithFixedState(Function<FPoint, Double> action);
    @Facade
    boolean toBooleanWithFixedState(Function<FPoint, Boolean> action);
}
