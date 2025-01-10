package eu.scattering.core.design.mutables.geometry.primitive.point;

import eu.scattering.core.design.annotations.Extension;
import eu.scattering.core.design.annotations.Facade;
import eu.scattering.core.design.annotations.Intermediate;
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
    boolean isNonDirectional();

    boolean isExact(double x, double y, double z);
    boolean isSimilar(double x, double y, double z);

    FPoint normalize();

    FPoint reflect();
    FPoint reflect(FPoint ref);

    double getLength();
    FPoint setLength(double length);

    double getDistance(FPoint ref);
    FPoint setDistance(FPoint ref, double distance);

    double getAngle(FPoint ref);
    FPoint setAngle(FPoint ref, double angle);

    double getDotProduct(FPoint ref);

    FPoint setCrossProduct(FPoint ref);

    FPoint setSphericalCoordinates(double inclination, double azimuth);

    double getInclination();
    FPoint setInclination(double inclination);

    double getAzimuth();
    FPoint setAzimuth(double azimuth);

    //--------------------------------------------------

    @Intermediate
    double getLengthP2();
    @Intermediate
    double getDistanceP2(FPoint ref);

    @Extension
    FPoint apply(Consumer<FPoint> action);

    @Facade
    double terminatorDouble(Function<FPoint, Double> action);
    @Facade
    boolean terminatorBoolean(Function<FPoint, Boolean> action);
}
