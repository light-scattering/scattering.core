package eu.scattering.core.design.mutables.geometry;

import eu.scattering.core.design.mutables.geometry.construct.ConstructEngineRot;
import eu.scattering.core.design.mutables.geometry.primitive.PrimitiveEngineRot;
import eu.scattering.core.design.mutables.geometry.shape.ShapeEngineRot;
import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;

public interface GeometryEngineRot extends ConstructEngineRot, PrimitiveEngineRot, ShapeEngineRot {

    Geometry rot(Geometry in, FRotQt core);
}
