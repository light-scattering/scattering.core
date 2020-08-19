package eu.scattering.core.logic.main.engine.extension;

import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.logic.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.logic.main.engine.base.point.FPoint;
import eu.scattering.core.logic.main.engine.base.vector.FVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Extension<T> {
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
