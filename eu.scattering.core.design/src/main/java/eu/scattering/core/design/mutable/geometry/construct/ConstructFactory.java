package eu.scattering.core.design.mutable.geometry.construct;

import eu.scattering.core.design.mutable.geometry.construct.line.FLineFactory;
import eu.scattering.core.design.mutable.geometry.construct.plane.FPlaneFactory;
import eu.scattering.core.design.mutable.geometry.construct.ray.FRayFactory;
import eu.scattering.core.design.mutable.geometry.construct.segment.FSegmentFactory;

public interface ConstructFactory extends FLineFactory, FRayFactory, FSegmentFactory, FPlaneFactory {
}
