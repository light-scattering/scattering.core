package eu.scattering.core.design.mutables.geometry;

import eu.scattering.core.design.mutables.geometry.primitive.PrimitiveFactory;
import eu.scattering.core.design.mutables.geometry.construct.ConstructFactory;
import eu.scattering.core.design.mutables.geometry.shape.ShapeFactory;

public interface GeometryFactory extends PrimitiveFactory, ConstructFactory, ShapeFactory {
}
