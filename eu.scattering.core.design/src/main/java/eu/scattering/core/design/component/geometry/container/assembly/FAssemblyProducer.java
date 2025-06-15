package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.util.support.Producer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FAssemblyProducer<T extends Geometry> extends Producer<FAssembly<T>> {

    @Override
    FAssembly<T> produce();
    @Override
    Stream<FAssembly<T>> stream();

    List<FAssembly<T>> getListAuto();
    List<FAssembly<T>> getListRandomized(int quantity);
    List<FAssembly<T>> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    FAssemblyProducer<T> withCustomRule(Function<FAssemblyFactory, FAssembly<T>> function, int weight);
    FAssemblyProducer<T> withCustomRule(BiFunction<FAssemblyFactory, FRandEngine, FAssembly<T>> function, int weight);

    // -------------------------------------------------------------------------------------------------

    default FAssemblyProducer<T> withCustomRule(Function<FAssemblyFactory, FAssembly<T>> function) {

        return withCustomRule(function, 1);
    }

    default FAssemblyProducer<T> withCustomRule(BiFunction<FAssemblyFactory, FRandEngine, FAssembly<T>> function) {

        return withCustomRule(function, 1);
    }
}
