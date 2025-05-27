package eu.scattering.core.design.component.geometry.construct.plane;

import java.util.function.Function;

public interface FPlaneProducer {

    FPlaneProducer setConfig(Function<FPlane, FPlane> function);
    FPlaneProducer addConfig(Function<FPlane, FPlane> function, double probability);

    FPlane produce();

    // -------------------------------------------------------------------------------------------------

    FPlaneProducer setPresetEmpty();

    FPlaneProducer setPresetUnitX();
    FPlaneProducer setPresetUnitY();
    FPlaneProducer setPresetUnitZ();
}
