package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public interface FRayProducer {

    void setConfig(Function<FRay, FRay> function);
    FRayProducer addConfig(Function<FRay, FRay> function, double probability);

    FRay produce();

    // -------------------------------------------------------------------------------------------------

    void setPresetOX();
    FRayProducer addPresetOX(double probability);

    void setPresetOY();
    FRayProducer addPresetOY(double probability);

    void setPresetOZ();
    FRayProducer addPresetOZ(double probability);
}
