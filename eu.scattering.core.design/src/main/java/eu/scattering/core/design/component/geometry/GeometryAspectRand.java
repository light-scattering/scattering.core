package eu.scattering.core.design.component.geometry;

import eu.scattering.core.design.component.geometry.construct.ConstructAspectRand;
import eu.scattering.core.design.component.geometry.base.BaseAspectRand;
import eu.scattering.core.design.component.geometry.container.ContainerAspectRand;
import eu.scattering.core.design.component.geometry.shape.ShapeAspectRand;

public interface GeometryAspectRand extends
        BaseAspectRand,
        ConstructAspectRand,
        ContainerAspectRand,
        ShapeAspectRand {
}
