package eu.scattering.core.design.mutable.geometry.construct;

import eu.scattering.core.design.mutable.geometry.construct.line.FLineEngineRot;
import eu.scattering.core.design.mutable.geometry.construct.plane.FPlaneEngineRot;
import eu.scattering.core.design.mutable.geometry.construct.ray.FRayEngineRot;
import eu.scattering.core.design.mutable.geometry.construct.segment.FSegmentEngineRot;

public interface ConstructEngineRot extends FLineEngineRot, FRayEngineRot, FSegmentEngineRot, FPlaneEngineRot {
}
