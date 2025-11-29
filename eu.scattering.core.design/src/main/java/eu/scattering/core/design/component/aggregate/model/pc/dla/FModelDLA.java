package eu.scattering.core.design.component.aggregate.model.pc.dla;

import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.lambda.TriConsumer;

public interface FModelDLA extends FModelPC {

    double getStep();
    void setStep(double step);

    double getSpawnFactor();
    void setSpawnFactor(double factor);

    double getExileFactor();
    void setExileFactor(double factor);

    // -------------------------------------------------------------------------------------------------

    TriConsumer<FAssembly<Shape>, FRandAspect, FPoint> getMovement();
    void setMovement(TriConsumer<FAssembly<Shape>, FRandAspect, FPoint> movement);
}
