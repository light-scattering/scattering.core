package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

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

    FPlaneProducer setPresetFixedPoint(FPos3D point);
}
