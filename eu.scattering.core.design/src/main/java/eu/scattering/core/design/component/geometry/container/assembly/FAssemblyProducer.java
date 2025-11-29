package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.extension.Producer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FAssemblyProducer<T extends Geometry> extends Producer<FAssembly<T>> {

    @Override
    FAssembly<T> produce();
    @Override
    List<FAssembly<T>> getList();
    @Override
    List<FAssembly<T>> getListRandomized(int quantity);
    @Override
    List<FAssembly<T>> getListFixed(int quantity);
    @Override
    Stream<FAssembly<T>> stream();

    // -------------------------------------------------------------------------------------------------

    FAssemblyProducer<T> withCustomRule(Function<FAssemblyFactory, FAssembly<T>> function, int weight);
    FAssemblyProducer<T> withCustomRule(BiFunction<FAssemblyFactory, FRandAspect, FAssembly<T>> function, int weight);

    // -------------------------------------------------------------------------------------------------

    default FAssemblyProducer<T> withCustomRule(Function<FAssemblyFactory, FAssembly<T>> function) {

        return withCustomRule(function, 1);
    }

    default FAssemblyProducer<T> withCustomRule(BiFunction<FAssemblyFactory, FRandAspect, FAssembly<T>> function) {

        return withCustomRule(function, 1);
    }
}
