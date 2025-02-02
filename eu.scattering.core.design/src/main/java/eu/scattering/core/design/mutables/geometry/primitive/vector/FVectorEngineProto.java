package eu.scattering.core.design.mutables.geometry.primitive.vector;

import eu.scattering.core.design.annotations.Facade;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FVectorEngineProto {

    @Facade
    FVector applyWithFixedState(FVector in, Consumer<FVector> action);
    @Facade
    FVector applyWithFixedMagnitude(FVector in, Consumer<FVector> action);
    @Facade
    FVector applyWithCenteredPosition(FVector in, Consumer<FVector> action);

    @Facade
    double toDoubleWithFixedState(FVector in, Function<FVector, Double> action);
    @Facade
    boolean toBooleanWithFixedState(FVector in, Function<FVector, Boolean> action);
}
