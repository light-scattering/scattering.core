package eu.scattering.core.design.component.aggregate.model.pc;

import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.function.BiConsumer;

public interface FModelPC extends FModel {

    void addMonitor(BiConsumer<Shape, Integer> monitor);
}
