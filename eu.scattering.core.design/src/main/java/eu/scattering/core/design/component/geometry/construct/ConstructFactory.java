package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.BaseFactory;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftFactory;
import eu.scattering.core.design.component.geometry.construct.line.FLineFactory;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneFactory;
import eu.scattering.core.design.component.geometry.construct.ray.FRayFactory;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentFactory;

public interface ConstructFactory extends BaseFactory,
        FDraftFactory,
        FLineFactory,
        FRayFactory,
        FSegmentFactory,
        FPlaneFactory {
}
