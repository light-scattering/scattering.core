package eu.scattering.core.design.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.construct.line.FLineEngineRand;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlaneEngineRand;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRayEngineRand;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegmentEngineRand;

public interface ConstructEngineRand extends FLineEngineRand, FRayEngineRand, FSegmentEngineRand, FPlaneEngineRand {
}
