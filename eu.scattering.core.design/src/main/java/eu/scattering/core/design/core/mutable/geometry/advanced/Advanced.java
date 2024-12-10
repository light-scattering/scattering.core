package eu.scattering.core.design.core.mutable.geometry.advanced;

import eu.scattering.core.design.core.mutable.geometry.Geometry;
import eu.scattering.core.design.core.mutable.geometry.simple.point.FPoint;
import eu.scattering.core.design.core.mutable.geometry.simple.vector.FVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Advanced<T> extends Geometry {

    FVector getOrigin();

    T setOriginRef(FVector origin);

    FPoint getBase();
    FPoint getHead();

    Consumer<Geometry> project();
    Consumer<Geometry> reflect();

    Function<Geometry, List<Double>> getDistance();
    Function<Geometry, List<Double>> getDistanceP2();
    Consumer<Geometry> setDistance(double distance);

    Function<Geometry, List<Boolean>> isPartOf();
}
