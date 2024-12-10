package eu.scattering.core.design.core.mutable.geometry;

import eu.scattering.core.design.core.mutable.geometry.simple.SimpleFactory;
import eu.scattering.core.design.core.mutable.geometry.advanced.AdvancedFactory;
import eu.scattering.core.design.core.mutable.geometry.shape.ShapeFactory;

public interface GeometryFactory extends SimpleFactory, AdvancedFactory, ShapeFactory {
}
