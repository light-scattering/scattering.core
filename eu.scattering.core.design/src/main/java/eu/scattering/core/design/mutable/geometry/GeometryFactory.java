package eu.scattering.core.design.mutable.geometry;

import eu.scattering.core.design.mutable.geometry.primitive.PrimitiveFactory;
import eu.scattering.core.design.mutable.geometry.construct.ConstructFactory;
import eu.scattering.core.design.mutable.geometry.shape.ShapeFactory;

public interface GeometryFactory extends PrimitiveFactory, ConstructFactory, ShapeFactory {
}
