package eu.scattering.core.design.component.aggregate.model.pc;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface FModelPC extends FModel {

    void addStepMonitor(BiConsumer<FAggregate, Shape> monitor);
    void addStepValidator(BiFunction<FAggregate, Shape, Boolean> validator);

    void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator);
}
