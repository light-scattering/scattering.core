package eu.scattering.core.design.component.number.quaternion;

import java.util.function.Function;

public interface FQuaternionProducer {

    FQuaternionProducer setConfig(Function<FQuaternion, FQuaternion> function, double probability);
    FQuaternionProducer addConfig(Function<FQuaternion, FQuaternion> function, double probability);

    FQuaternion produce();

    // -------------------------------------------------------------------------------------------------

    FQuaternionProducer setPresetDefault();

    // -------------------------------------------------------------------------------------------------

    default FQuaternionProducer setConfig(Function<FQuaternion, FQuaternion> function) {

        return setConfig(function, 1);
    }
}
