package eu.scattering.core.design.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.construct.line.FLineFactory;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlaneFactory;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRayFactory;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegmentFactory;

public interface ConstructFactory extends FLineFactory, FRayFactory, FSegmentFactory, FPlaneFactory {
}
