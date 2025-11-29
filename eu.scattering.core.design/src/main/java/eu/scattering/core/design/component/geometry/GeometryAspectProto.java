package eu.scattering.core.design.component.geometry;

import eu.scattering.core.design.component.geometry.construct.ConstructAspectProto;
import eu.scattering.core.design.component.geometry.base.BaseAspectProto;
import eu.scattering.core.design.component.geometry.container.ContainerAspectProto;
import eu.scattering.core.design.component.geometry.shape.ShapeAspectProto;

public interface GeometryAspectProto extends
        BaseAspectProto,
        ConstructAspectProto,
        ContainerAspectProto,
        ShapeAspectProto {
}
