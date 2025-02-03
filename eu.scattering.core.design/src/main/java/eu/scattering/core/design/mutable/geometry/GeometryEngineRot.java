package eu.scattering.core.design.mutable.geometry;

import eu.scattering.core.design.mutable.geometry.construct.ConstructEngineRot;
import eu.scattering.core.design.mutable.geometry.primitive.PrimitiveEngineRot;
import eu.scattering.core.design.mutable.geometry.shape.ShapeEngineRot;
import eu.scattering.core.transfer.container.engine.FRotQt.FRotQt;

public interface GeometryEngineRot extends ConstructEngineRot, PrimitiveEngineRot, ShapeEngineRot {

    Geometry rot(Geometry in, FRotQt core);
}
