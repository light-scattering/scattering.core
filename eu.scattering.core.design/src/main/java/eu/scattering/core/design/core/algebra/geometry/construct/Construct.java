package eu.scattering.core.design.core.algebra.geometry.construct;

import eu.scattering.core.design.core.algebra.Algebra;
import eu.scattering.core.design.core.algebra.geometry.Geometry;
import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.core.algebra.geometry.primitive.vector.FVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Construct<T> extends Geometry, Algebra<T> {

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
