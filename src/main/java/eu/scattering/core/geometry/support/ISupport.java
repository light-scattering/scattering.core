package eu.scattering.core.geometry.support;

import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.main.base.vector.IFVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ISupport<T> {

    T setOriginRef(IFVector origin);

    IFVector getOrigin();

    Consumer<IBaseExtensionAssembly> project();

    Consumer<IBaseExtensionAssembly> reflect();

    Function<IBaseExtensionAssembly, List<Boolean>> isPartOf();

    Function<IBaseExtensionAssembly, List<Double>> getDistance();

    Consumer<IBaseExtensionAssembly> setDistance(double distance) throws IllegalArgumentException;

}
