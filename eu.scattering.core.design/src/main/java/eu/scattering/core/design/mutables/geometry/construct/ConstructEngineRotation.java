package eu.scattering.core.design.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.construct.line.FLineEngineRotation;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlaneEngineRotation;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRayEngineRotation;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegmentEngineRotation;

public interface ConstructEngineRotation extends FLineEngineRotation, FRayEngineRotation, FSegmentEngineRotation, FPlaneEngineRotation {
}
