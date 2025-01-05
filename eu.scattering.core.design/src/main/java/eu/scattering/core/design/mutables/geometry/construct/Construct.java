package eu.scattering.core.design.mutables.geometry.construct;

import eu.scattering.core.design.mutables.Mutable;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;

import java.util.List;

public interface Construct<T> extends Geometry, Mutable<T> {

    FVector getOrigin();

    T setOriginRef(FVector origin);

    FPoint getBase();
    FPoint getHead();

    void project(Geometry geometry);
    void reflect(Geometry geometry);

    List<Double> getDistance(Geometry geometry);
    List<Double> getDistanceP2(Geometry geometry);

    void setDistance(Geometry geometry, double distance);

    List<Boolean> isPartOf(Geometry geometry);
}
