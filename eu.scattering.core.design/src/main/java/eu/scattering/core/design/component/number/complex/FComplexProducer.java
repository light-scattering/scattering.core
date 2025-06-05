package eu.scattering.core.design.component.number.complex;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FComplexProducer extends Iterable<FComplex> {

    FComplex produce();
    Stream<FComplex> stream();

    // -------------------------------------------------------------------------------------------------

    FComplexProducer withCustomRule(Function<FComplexFactory, FComplex> function, int weight);

    FComplexProducer withZero(int weight);

    // -------------------------------------------------------------------------------------------------

    default FComplexProducer withCustomRule(Function<FComplexFactory, FComplex> function) {

        return withCustomRule(function, 1);
    }

    default FComplexProducer withZero() {

        return withZero(1);
    }
}
