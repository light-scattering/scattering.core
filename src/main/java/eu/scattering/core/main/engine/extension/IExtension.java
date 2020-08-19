package eu.scattering.core.main.engine.extension;

import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.main.engine.base.IBaseExtensionAssembly;
import eu.scattering.core.main.engine.base.point.IFPoint;
import eu.scattering.core.main.engine.base.vector.IFVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IExtension<T> {
// TODO - Finish DirectionException
    IFVector getOrigin();
    T setOriginRef(IFVector origin);

    IFPoint getBase();
    IFPoint getHead();

    Consumer<IBaseExtensionAssembly> project();
    Consumer<IBaseExtensionAssembly> reflect();
    Consumer<IBaseExtensionAssembly> setDistance(double distance) throws DirectionException;

    Function<IBaseExtensionAssembly, List<Double>> getDistance();

    Function<IBaseExtensionAssembly, List<Boolean>> isPartOf();
}
