package eu.scattering.core.design.main.algebra.engine.extension;

import eu.scattering.core.design.main.algebra.engine.Engine;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Extension<T> {

    FVector getOrigin();

    T setOriginRef(FVector origin);

    FPoint getBase();
    FPoint getHead();

    Consumer<Engine> project();
    Consumer<Engine> reflect();

    Function<Engine, List<Double>> getDistance();
    Consumer<Engine> setDistance(double distance);

    Function<Engine, List<Boolean>> isPartOf();
}
