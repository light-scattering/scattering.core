package eu.scattering.core.design.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.construct.line.FLineEngineRandom;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlaneEngineRandom;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRayEngineRandom;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegmentEngineRandom;

public interface ConstructEngineRandom extends FLineEngineRandom, FRayEngineRandom, FSegmentEngineRandom, FPlaneEngineRandom {
}
