package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.component.geometry.Geometry;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FAssemblyProducer<T extends Geometry> {

    FAssembly<T> produce();

    Stream<FAssembly<T>> stream();

    List<FAssembly<T>> getListAuto();
    List<FAssembly<T>> getListRandomized(int quantity);
    List<FAssembly<T>> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    FAssemblyProducer<T> withCustomRule(Function<FAssemblyFactory, FAssembly<T>> function, int weight);

    // -------------------------------------------------------------------------------------------------

    default FAssemblyProducer<T> withCustomRule(Function<FAssemblyFactory, FAssembly<T>> function) {

        return withCustomRule(function, 1);
    }
}
