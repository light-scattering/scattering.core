package eu.scattering.core.design.component.number.complex;

import java.util.function.Function;

public interface FComplexProducer {

    FComplexProducer setConfig(Function<FComplex, FComplex> function, double probability);
    FComplexProducer addConfig(Function<FComplex, FComplex> function, double probability);

    FComplex produce();

    // -------------------------------------------------------------------------------------------------

    FComplexProducer setPresetDefault();

    // -------------------------------------------------------------------------------------------------

    default FComplexProducer setConfig(Function<FComplex, FComplex> function) {

        return setConfig(function, 1);
    }
}
