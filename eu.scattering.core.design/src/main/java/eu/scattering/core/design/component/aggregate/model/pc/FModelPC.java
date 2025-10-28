package eu.scattering.core.design.component.aggregate.model.pc;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface FModelPC extends FModel {

    void addStepMonitor(BiConsumer<FAssembly<Shape>, Shape> monitor);
    void addStepValidator(BiFunction<FAssembly<Shape>, Shape, Boolean> validator);

    void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator);
}
