package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.line.FLineEngineRand;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneEngineRand;
import eu.scattering.core.design.component.geometry.construct.ray.FRayEngineRand;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentEngineRand;

public interface ConstructEngineRand extends FLineEngineRand, FRayEngineRand, FSegmentEngineRand, FPlaneEngineRand {
}
