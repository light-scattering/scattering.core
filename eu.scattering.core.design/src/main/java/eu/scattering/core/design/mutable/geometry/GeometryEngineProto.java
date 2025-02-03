package eu.scattering.core.design.mutable.geometry;

import eu.scattering.core.design.mutable.geometry.construct.ConstructEngineProto;
import eu.scattering.core.design.mutable.geometry.primitive.PrimitiveEngineProto;
import eu.scattering.core.design.mutable.geometry.shape.ShapeEngineProto;

public interface GeometryEngineProto extends PrimitiveEngineProto, ConstructEngineProto, ShapeEngineProto {
}
