package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.draft.FDraftAspectRand;
import eu.scattering.core.design.component.geometry.construct.line.FLineAspectRand;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneAspectRand;
import eu.scattering.core.design.component.geometry.construct.ray.FRayAspectRand;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentAspectRand;

public interface ConstructAspectRand extends
        FDraftAspectRand,
        FLineAspectRand,
        FRayAspectRand,
        FSegmentAspectRand,
        FPlaneAspectRand {
}
