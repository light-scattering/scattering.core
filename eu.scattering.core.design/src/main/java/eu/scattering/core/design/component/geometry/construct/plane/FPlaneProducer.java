package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public interface FPlaneProducer {

    void setConfig(Function<FPlane, FPlane> function);
    FPlaneProducer addConfig(Function<FPlane, FPlane> function, double probability);

    FPlane produce();

    // -------------------------------------------------------------------------------------------------

    void setPresetDirX();
    FPlaneProducer addPresetDirX(double probability);

    void setPresetDirY();
    FPlaneProducer addPresetDirY(double probability);

    void setPresetDirZ();
    FPlaneProducer addPresetDirZ(double probability);
}
