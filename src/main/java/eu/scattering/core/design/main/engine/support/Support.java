package eu.scattering.core.design.main.engine.support;

import eu.scattering.core.design.main.engine.base.BaseComposite;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.base.vector.FVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Support<T> {

    FVector getOrigin();

    T setOriginRef(FVector origin);

    FPoint getBase();
    FPoint getHead();

    Consumer<BaseComposite> project();
    Consumer<BaseComposite> reflect();

    Function<BaseComposite, List<Double>> getDistance();
    Consumer<BaseComposite> setDistance(double distance);

    Function<BaseComposite, List<Boolean>> isPartOf();
}
