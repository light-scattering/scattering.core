package eu.scattering.core.design.main.mutable.geometry.extension;

import eu.scattering.core.design.main.mutable.geometry.Geometry;
import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.mutable.geometry.base.vector.FVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Extension<T> extends Geometry {

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
