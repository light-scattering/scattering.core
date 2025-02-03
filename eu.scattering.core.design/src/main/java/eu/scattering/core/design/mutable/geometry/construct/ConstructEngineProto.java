package eu.scattering.core.design.mutable.geometry.construct;

import eu.scattering.core.design.mutable.geometry.construct.line.FLineEngineProto;
import eu.scattering.core.design.mutable.geometry.construct.plane.FPlaneEngineProto;
import eu.scattering.core.design.mutable.geometry.construct.ray.FRayEngineProto;
import eu.scattering.core.design.mutable.geometry.construct.segment.FSegmentEngineProt;

public interface ConstructEngineProto extends FLineEngineProto, FRayEngineProto, FSegmentEngineProt, FPlaneEngineProto {
}
