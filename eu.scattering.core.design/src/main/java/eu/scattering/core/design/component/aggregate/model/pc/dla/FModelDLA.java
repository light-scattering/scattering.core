package eu.scattering.core.design.component.aggregate.model.pc.dla;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.util.lambda.TriConsumer;
import eu.scattering.core.design.util.lambda.TriFunction;

public interface FModelDLA extends FModelPC {

    void setStep(double step);

    void setSpawnFactor(double factor);
    void setExileFactor(double factor);

    void setMovement(TriConsumer<FAssembly<Shape>, FRandEngine, FPoint> movement);
    void setValidation(TriFunction<FAssembly<Shape>, FRandEngine, Shape, Boolean> validation);
}
