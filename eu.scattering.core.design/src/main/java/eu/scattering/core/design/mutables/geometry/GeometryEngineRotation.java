package eu.scattering.core.design.mutables.geometry;

import eu.scattering.core.design.mutables.geometry.construct.ConstructEngineRotation;
import eu.scattering.core.design.mutables.geometry.primitive.PrimitiveEngineRotation;
import eu.scattering.core.design.mutables.geometry.shape.ShapeEngineRotation;
import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;

public interface GeometryEngineRotation extends ConstructEngineRotation, PrimitiveEngineRotation, ShapeEngineRotation {

    Geometry rot(Geometry in, FRotQt core);
}
