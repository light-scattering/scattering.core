package eu.scattering.core.geometry.support;

import eu.scattering.core.geometry.main.IGeometryAssembly;
import eu.scattering.core.geometry.main.base.vector.IFVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IGeometrySupport<T> {

    T setOriginRef(IFVector origin);

    IFVector getOrigin();

    Consumer<IGeometryAssembly> project();

    Consumer<IGeometryAssembly> reflect();

    Function<IGeometryAssembly, List<Boolean>> isCloseTo();

    Function<IGeometryAssembly, List<Double>> getDistance();

}
