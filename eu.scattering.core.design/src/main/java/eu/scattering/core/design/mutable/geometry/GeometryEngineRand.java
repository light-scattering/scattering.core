package eu.scattering.core.design.mutable.geometry;

import eu.scattering.core.design.mutable.geometry.construct.ConstructEngineRand;
import eu.scattering.core.design.mutable.geometry.primitive.PrimitiveEngineRand;
import eu.scattering.core.design.mutable.geometry.shape.ShapeEngineRand;

public interface GeometryEngineRand extends ConstructEngineRand, PrimitiveEngineRand, ShapeEngineRand {
}
