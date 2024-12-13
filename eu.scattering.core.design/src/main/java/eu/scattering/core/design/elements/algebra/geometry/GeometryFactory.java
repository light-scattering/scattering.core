package eu.scattering.core.design.elements.algebra.geometry;

import eu.scattering.core.design.elements.algebra.geometry.primitive.PrimitiveFactory;
import eu.scattering.core.design.elements.algebra.geometry.construct.ConstructFactory;
import eu.scattering.core.design.elements.algebra.geometry.shape.ShapeFactory;

public interface GeometryFactory extends PrimitiveFactory, ConstructFactory, ShapeFactory {
}
