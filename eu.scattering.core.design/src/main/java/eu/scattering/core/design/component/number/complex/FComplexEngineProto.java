package eu.scattering.core.design.component.number.complex;

import eu.scattering.core.design.util.annotation.Facade;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FComplexEngineProto {

    @Facade
    FComplex applyWithFixedState(FComplex in, Consumer<FComplex> action);

    @Facade
    double toDoubleWithFixedState(FComplex in, Function<FComplex, Double> action);
    @Facade
    boolean toBooleanWithFixedState(FComplex in, Function<FComplex, Boolean> action);
}
