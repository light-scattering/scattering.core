package eu.scattering.core.design.main.algebra.engine;

import eu.scattering.core.design.main.algebra.engine.base.BaseFactory;
import eu.scattering.core.design.main.algebra.engine.extension.ExtensionFactory;
import eu.scattering.core.design.main.algebra.engine.shape.ShapeFactory;

public interface EngineFactory extends BaseFactory, ExtensionFactory, ShapeFactory {
}
