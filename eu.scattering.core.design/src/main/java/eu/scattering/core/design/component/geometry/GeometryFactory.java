package eu.scattering.core.design.component.geometry;

import eu.scattering.core.design.component.geometry.base.BaseFactory;
import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.container.ContainerFactory;
import eu.scattering.core.design.component.geometry.shape.ShapeFactory;

public interface GeometryFactory extends
        BaseFactory,
        ConstructFactory,
        ContainerFactory,
        ShapeFactory {
}
