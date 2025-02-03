package eu.scattering.core.design.mutable.geometry.construct;

import eu.scattering.core.design.mutable.geometry.construct.line.FLineEngineRand;
import eu.scattering.core.design.mutable.geometry.construct.plane.FPlaneEngineRand;
import eu.scattering.core.design.mutable.geometry.construct.ray.FRayEngineRand;
import eu.scattering.core.design.mutable.geometry.construct.segment.FSegmentEngineRand;

public interface ConstructEngineRand extends FLineEngineRand, FRayEngineRand, FSegmentEngineRand, FPlaneEngineRand {
}
