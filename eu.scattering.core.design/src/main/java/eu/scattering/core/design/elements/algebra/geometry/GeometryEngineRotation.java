package eu.scattering.core.design.elements.algebra.geometry;

import eu.scattering.core.design.elements.algebra.geometry.construct.ConstructEngineRotation;
import eu.scattering.core.design.elements.algebra.geometry.primitive.PrimitiveEngineRotation;
import eu.scattering.core.design.elements.algebra.geometry.shape.ShapeEngineRotation;
import eu.scattering.core.transfer.containers.engine.FRot.FRot;

public interface GeometryEngineRotation extends ConstructEngineRotation, PrimitiveEngineRotation, ShapeEngineRotation {

    Geometry rotate(Geometry geometry, FRot core);
}
