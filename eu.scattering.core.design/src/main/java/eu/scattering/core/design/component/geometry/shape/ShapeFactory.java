package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereFactory;

public interface ShapeFactory extends ConstructFactory, FSphereFactory {

    ShapeProducer getShapeProducer();
}
