package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public interface FPlaneProducer {

    FPlaneProducer setConfig(Function<FPlane, FPlane> function, double probability);
    FPlaneProducer addConfig(Function<FPlane, FPlane> function, double probability);

    FPlane produce();

    // -------------------------------------------------------------------------------------------------

    FPlaneProducer setPresetDirX();
    FPlaneProducer setPresetDirY();
    FPlaneProducer setPresetDirZ();

    FPlaneProducer setPresetFixedPoint(FPos3D point);

    // -------------------------------------------------------------------------------------------------

    default FPlaneProducer setConfig(Function<FPlane, FPlane> function) {

        return setConfig(function, 1);
    }
}
