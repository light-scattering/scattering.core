package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.util.annotation.Facade;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FVectorEngineProto {

    @Facade
    FVector withFixedState(FVector in, Consumer<FVector> action);
    @Facade
    FVector withFixedMagnitude(FVector in, Consumer<FVector> action);
    @Facade
    FVector withCenteredPosition(FVector in, Consumer<FVector> action);

    @Facade
    double toDoubleWithFixedState(FVector in, Function<FVector, Double> action);
    @Facade
    boolean toBooleanWithFixedState(FVector in, Function<FVector, Boolean> action);
}
