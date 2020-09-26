package eu.scattering.core.test.design.main.mutable.geometry;

import eu.scattering.core.test.design.main.mutable.geometry.base.BaseFactory;
import eu.scattering.core.test.design.main.mutable.geometry.extension.ExtensionFactory;
import eu.scattering.core.test.design.main.mutable.geometry.shape.ShapeFactory;

public interface GeometryFactory extends BaseFactory, ExtensionFactory, ShapeFactory {
}
