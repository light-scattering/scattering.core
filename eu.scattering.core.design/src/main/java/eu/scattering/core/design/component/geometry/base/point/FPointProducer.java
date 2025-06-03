package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.Function;

public interface FPointProducer {

    void setConfig(Function<FPoint, FPoint> function);
    FPointProducer addConfig(Function<FPoint, FPoint> function, double probability);

    FPoint produce();

    // -------------------------------------------------------------------------------------------------

    void setPresetZero();
    FPointProducer addPresetZero(double probability);

    void setPresetInSphere(double radius);
    FPointProducer addPresetInSphere(double radius, double probability);

    void setPresetOnSphere(double radius);
    FPointProducer addPresetOnSphere(double radius, double probability);

    void setPresetRange(FPairPos3D range);
    FPointProducer addPresetInRange(FPairPos3D range, double probability);
}
