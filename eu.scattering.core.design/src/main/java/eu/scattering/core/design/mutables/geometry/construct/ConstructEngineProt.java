package eu.scattering.core.design.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.construct.line.FLineEngineProt;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlaneEngineProt;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRayEngineProt;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegmentEngineProt;

public interface ConstructEngineProt extends FLineEngineProt, FRayEngineProt, FSegmentEngineProt, FPlaneEngineProt {
}
