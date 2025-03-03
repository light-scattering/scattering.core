package eu.scattering.core.design.component.geometry;

import eu.scattering.core.design.component.geometry.construct.ConstructEngineRot;
import eu.scattering.core.design.component.geometry.base.BaseEngineRot;
import eu.scattering.core.design.component.geometry.shape.ShapeEngineRot;
import eu.scattering.core.transfer.container.storage.FRotQt.FRotQt;

public interface GeometryEngineRot extends ConstructEngineRot, BaseEngineRot, ShapeEngineRot {

    Geometry rot(Geometry in, FRotQt core);
}
