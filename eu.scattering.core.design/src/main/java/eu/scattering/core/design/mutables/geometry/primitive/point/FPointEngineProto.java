package eu.scattering.core.design.mutables.geometry.primitive.point;

import eu.scattering.core.design.annotations.Facade;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FPointEngineProto {

    @Facade
    FPoint applyWithFixedState(FPoint in, Consumer<FPoint> action);
    @Facade
    FPoint applyWithFixedMagnitude(FPoint in, Consumer<FPoint> action);

    @Facade
    double toDoubleWithFixedState(FPoint in, Function<FPoint, Double> action);
    @Facade
    boolean toBooleanWithFixedState(FPoint in, Function<FPoint, Boolean> action);
}
