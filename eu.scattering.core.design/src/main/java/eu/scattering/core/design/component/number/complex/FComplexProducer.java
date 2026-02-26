package eu.scattering.core.design.component.number.complex;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.functionality.Producer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FComplexProducer extends Producer<FComplex> {

    @Override
    FComplex produce();
    @Override
    List<FComplex> getList();
    @Override
    List<FComplex> getListRandomized(int quantity);
    @Override
    List<FComplex> getListFixed(int quantity);
    @Override
    Stream<FComplex> stream();

    // -------------------------------------------------------------------------------------------------

    FComplexProducer withCustomRule(Function<FComplexFactory, FComplex> function, int weight);
    FComplexProducer withCustomRule(BiFunction<FComplexFactory, FRandGenerator, FComplex> function, int weight);

    FComplexProducer withZero(int weight);

    // -------------------------------------------------------------------------------------------------

    default FComplexProducer withCustomRule(Function<FComplexFactory, FComplex> function) {

        return withCustomRule(function, 1);
    }

    default FComplexProducer withCustomRule(BiFunction<FComplexFactory, FRandGenerator, FComplex> function) {

        return withCustomRule(function, 1);
    }

    default FComplexProducer withZero() {

        return withZero(1);
    }
}
