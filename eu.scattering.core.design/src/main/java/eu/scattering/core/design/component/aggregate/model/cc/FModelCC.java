package eu.scattering.core.design.component.aggregate.model.cc;

import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.List;

public interface FModelCC extends FModel {

    void buildStep(List<List<Shape>> field);
}
