package eu.scattering.core.design.component.number.complex;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FComplexProducer {

    FComplex produce();

    Stream<FComplex> stream();

    List<FComplex> getListAuto();
    List<FComplex> getListRandomized(int quantity);
    List<FComplex> getListFixed(int quantity);

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
