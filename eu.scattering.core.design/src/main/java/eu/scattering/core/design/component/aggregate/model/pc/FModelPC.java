package eu.scattering.core.design.component.aggregate.model.pc;

import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.List;
import java.util.Queue;

public interface FModelPC extends FModel {

    boolean buildStep(List<Shape> aggregated, Queue<Shape> remaining);
}
