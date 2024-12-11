package eu.scattering.core.design.core.algebra.geometry;

import eu.scattering.core.design.core.algebra.geometry.primitive.PrimitiveFactory;
import eu.scattering.core.design.core.algebra.geometry.construct.ConstructFactory;
import eu.scattering.core.design.core.algebra.geometry.shape.ShapeFactory;

public interface GeometryFactory extends PrimitiveFactory, ConstructFactory, ShapeFactory {
}
