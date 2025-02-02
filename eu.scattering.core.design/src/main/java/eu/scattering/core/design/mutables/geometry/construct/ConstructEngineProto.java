package eu.scattering.core.design.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.construct.line.FLineEngineProto;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlaneEngineProto;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRayEngineProto;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegmentEngineProt;

public interface ConstructEngineProto extends FLineEngineProto, FRayEngineProto, FSegmentEngineProt, FPlaneEngineProto {
}
