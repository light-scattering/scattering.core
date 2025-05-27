package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public interface FRayProducer {

    FRayProducer setConfig(Function<FRay, FRay> function);
    FRayProducer addConfig(Function<FRay, FRay> function, double probability);

    FRay produce();

    // -------------------------------------------------------------------------------------------------

    FRayProducer setPresetEmpty();

    FRayProducer setPresetUnitX();
    FRayProducer setPresetUnitY();
    FRayProducer setPresetUnitZ();

    FRayProducer setPresetFixedPoint(FPos3D point);
}
