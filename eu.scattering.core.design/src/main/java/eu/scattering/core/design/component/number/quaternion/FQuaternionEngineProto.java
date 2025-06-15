package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.design.util.annotation.Facade;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FQuaternionEngineProto {

    @Facade
    FQuaternion applyWithFixedState(FQuaternion in, Consumer<FQuaternion> action);

    @Facade
    double toDoubleWithFixedState(FQuaternion in, Function<FQuaternion, Double> action);
    @Facade
    boolean toBooleanWithFixedState(FQuaternion in, Function<FQuaternion, Boolean> action);
}
