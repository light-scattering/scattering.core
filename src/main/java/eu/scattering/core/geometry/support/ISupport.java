package eu.scattering.core.geometry.support;

import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.main.base.vector.IFVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ISupport<T> {

    IFVector getOrigin();
    T setOriginRef(IFVector origin);

    Consumer<IBaseExtensionAssembly> project();
    Consumer<IBaseExtensionAssembly> reflect();
    Consumer<IBaseExtensionAssembly> setDistance(double distance) throws IllegalArgumentException;

    Function<IBaseExtensionAssembly, List<Double>> getDistance();

    Function<IBaseExtensionAssembly, List<Boolean>> isPartOf();
}
