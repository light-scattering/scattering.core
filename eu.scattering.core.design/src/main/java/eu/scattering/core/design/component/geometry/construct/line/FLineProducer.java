package eu.scattering.core.design.component.geometry.construct.line;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.functionality.Producer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FLineProducer extends Producer<FLine> {

    @Override
    FLine produce();
    @Override
    List<FLine> getList();
    @Override
    List<FLine> getListRandomized(int quantity);
    @Override
    List<FLine> getListFixed(int quantity);
    @Override
    Stream<FLine> stream();

    // -------------------------------------------------------------------------------------------------

    FLineProducer withCustomRule(Function<FLineFactory, FLine> function, int weight);
    FLineProducer withCustomRule(BiFunction<FLineFactory, FRandAspect, FLine> function, int weight);

    FLineProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FLineProducer withCustomRule(Function<FLineFactory, FLine> function) {

        return withCustomRule(function, 1);
    }

    default FLineProducer withCustomRule(BiFunction<FLineFactory, FRandAspect, FLine> function) {

        return withCustomRule(function, 1);
    }

    default FLineProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
