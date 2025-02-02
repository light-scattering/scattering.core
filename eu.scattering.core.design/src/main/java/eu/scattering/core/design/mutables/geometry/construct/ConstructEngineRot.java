package eu.scattering.core.design.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.construct.line.FLineEngineRot;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlaneEngineRot;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRayEngineRot;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegmentEngineRot;

public interface ConstructEngineRot extends FLineEngineRot, FRayEngineRot, FSegmentEngineRot, FPlaneEngineRot {
}
