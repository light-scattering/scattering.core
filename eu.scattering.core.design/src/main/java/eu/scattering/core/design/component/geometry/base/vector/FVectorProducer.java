package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public interface FVectorProducer {

    FVectorProducer setConfig(Function<FVector, FVector> function, double probability);
    FVectorProducer addConfig(Function<FVector, FVector> function, double probability);

    FVector produce();

    // -------------------------------------------------------------------------------------------------

    FVectorProducer setPresetUnitX();
    FVectorProducer setPresetUnitY();
    FVectorProducer setPresetUnitZ();

    FVectorProducer setPresetInRange(FPos3D base, FPairPos3D range);

    FVectorProducer setPresetInSphere(FPos3D base, double radius);
    FVectorProducer setPresetOnSphere(FPos3D base, double radius);

    // -------------------------------------------------------------------------------------------------

    default FVectorProducer setConfig(Function<FVector, FVector> function) {

        return setConfig(function, 1);
    }
}
