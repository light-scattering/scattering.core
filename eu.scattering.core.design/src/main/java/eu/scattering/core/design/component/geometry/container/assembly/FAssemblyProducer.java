package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.component.geometry.Geometry;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FAssemblyProducer<T extends Geometry> extends Iterable<FAssembly<T>> {

    FAssembly<T> produce();
    Stream<FAssembly<T>> stream();

    // -------------------------------------------------------------------------------------------------

    FAssemblyProducer<T> withCustomRule(Function<FAssemblyFactory, FAssembly<T>> function, int weight);

    // -------------------------------------------------------------------------------------------------

    default FAssemblyProducer<T> withCustomRule(Function<FAssemblyFactory, FAssembly<T>> function) {

        return withCustomRule(function, 1);
    }
}
