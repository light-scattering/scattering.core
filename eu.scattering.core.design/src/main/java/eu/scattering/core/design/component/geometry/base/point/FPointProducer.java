package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.Function;

public interface FPointProducer {

    FPointProducer setConfig(Function<FPoint, FPoint> function, double probability);
    FPointProducer addConfig(Function<FPoint, FPoint> function, double probability);

    FPoint produce();

    // -------------------------------------------------------------------------------------------------

    FPointProducer setPresetDefault();

    FPointProducer setPresetInRange(FPairPos3D range);
    FPointProducer setPresetInSphere(double radius);
    FPointProducer setPresetOnSphere(double radius);

    // -------------------------------------------------------------------------------------------------

    default FPointProducer setConfig(Function<FPoint, FPoint> function) {

        return setConfig(function, 1);
    }
}
