package eu.scattering.core.design.mutables.algebra.geometry;

import eu.scattering.core.design.mutables.algebra.geometry.primitive.PrimitiveFactory;
import eu.scattering.core.design.mutables.algebra.geometry.construct.ConstructFactory;
import eu.scattering.core.design.mutables.algebra.geometry.shape.ShapeFactory;

public interface GeometryFactory extends PrimitiveFactory, ConstructFactory, ShapeFactory {
}
