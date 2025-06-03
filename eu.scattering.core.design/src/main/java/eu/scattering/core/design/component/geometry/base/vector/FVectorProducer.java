package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.Function;

public interface FVectorProducer {

    void setConfig(Function<FVector, FVector> function);
    FVectorProducer addConfig(Function<FVector, FVector> function, double probability);

    FVector produce();

    // -------------------------------------------------------------------------------------------------

    void setPresetUnitX();
    FVectorProducer addPresetUnitX(double probability);

    void setPresetUnitY();
    FVectorProducer addPresetUnitY(double probability);

    void setPresetUnitZ();
    FVectorProducer addPresetUnitZ(double probability);

    void setPresetInSphere(double radius);
    FVectorProducer addPresetInSphere(double radius, double probability);

    void setPresetOnSphere(double radius);
    FVectorProducer addPresetOnSphere(double radius, double probability);

    void setPresetRange(FPairPos3D range);
    FVectorProducer addPresetInRange(FPairPos3D range, double probability);
}
