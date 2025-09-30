package eu.scattering.core.design.component.aggregate.model.pc;

import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.util.lambda.TriFunction;

import java.util.function.BiConsumer;

public interface FModelPC extends FModel {

    void setMonitor(BiConsumer<Shape, Integer> monitor);
    void setValidator(TriFunction<FAssembly<Shape>, FRandEngine, Shape, Boolean> validation);
}
