package eu.scattering.core.design.main.engine.support;

import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.design.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.base.vector.FVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Support<T> {
// TODO - Finish DirectionException
    FVector getOrigin();
    T setOriginRef(FVector origin);

    FPoint getBase();
    FPoint getHead();

    Consumer<BaseExtensionAssembly> project();
    Consumer<BaseExtensionAssembly> reflect();
    Consumer<BaseExtensionAssembly> setDistance(double distance) throws DirectionException;

    Function<BaseExtensionAssembly, List<Double>> getDistance();

    Function<BaseExtensionAssembly, List<Boolean>> isPartOf();
}
