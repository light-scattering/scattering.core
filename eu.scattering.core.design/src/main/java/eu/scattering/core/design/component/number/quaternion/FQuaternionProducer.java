package eu.scattering.core.design.component.number.quaternion;

import java.util.function.Function;

public interface FQuaternionProducer {

    FQuaternionProducer setConfig(Function<FQuaternion, FQuaternion> function);
    FQuaternionProducer addConfig(Function<FQuaternion, FQuaternion> function, double probability);

    FQuaternion produce();

    // -------------------------------------------------------------------------------------------------

    FQuaternionProducer setPresetEmpty();
}
