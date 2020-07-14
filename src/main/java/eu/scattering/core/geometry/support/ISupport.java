package eu.scattering.core.geometry.support;

import eu.scattering.core.exception.DirectionException;
import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ISupport<T> {
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
