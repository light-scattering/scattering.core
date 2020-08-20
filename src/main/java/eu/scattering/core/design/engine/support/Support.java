package eu.scattering.core.design.engine.support;

import eu.scattering.core.design.engine.base.BaseExtensionAssembly;
import eu.scattering.core.design.engine.base.point.FPoint;
import eu.scattering.core.design.engine.base.vector.FVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Support<T> {

    FVector getOrigin();
    T setOriginRef(FVector origin);

    FPoint getBase();
    FPoint getHead();

    Consumer<BaseExtensionAssembly> project();
    Consumer<BaseExtensionAssembly> reflect();
    Consumer<BaseExtensionAssembly> setDistance(double distance) throws IllegalStateException;

    Function<BaseExtensionAssembly, List<Double>> getDistance();

    Function<BaseExtensionAssembly, List<Boolean>> isPartOf();
}
