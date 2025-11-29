package eu.scattering.core.design.component.geometry;

import eu.scattering.core.design.component.geometry.construct.ConstructAspectRot;
import eu.scattering.core.design.component.geometry.base.BaseAspectRot;
import eu.scattering.core.design.component.geometry.container.ContainerAspectRot;
import eu.scattering.core.design.component.geometry.shape.ShapeAspectRot;
import eu.scattering.core.design.transfer.complex.FRotQt;

public interface GeometryAspectRot extends
        BaseAspectRot,
        ConstructAspectRot,
        ContainerAspectRot,
        ShapeAspectRot {

    Geometry rot(Geometry in, FRotQt core);
}
