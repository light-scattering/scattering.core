package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.util.annotation.Facade;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FPointEngineProto {

    @Facade
    FPoint withFixedState(FPoint in, Consumer<FPoint> action);
    @Facade
    FPoint withFixedMagnitude(FPoint in, Consumer<FPoint> action);

    @Facade
    double toDoubleWithFixedState(FPoint in, Function<FPoint, Double> action);
    @Facade
    boolean toBooleanWithFixedState(FPoint in, Function<FPoint, Boolean> action);
}
