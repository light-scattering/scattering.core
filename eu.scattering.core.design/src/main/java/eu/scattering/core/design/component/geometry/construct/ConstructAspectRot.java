package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.draft.FDraftAspectRot;
import eu.scattering.core.design.component.geometry.construct.line.FLineAspectRot;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneAspectRot;
import eu.scattering.core.design.component.geometry.construct.ray.FRayAspectRot;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentAspectRot;

public interface ConstructAspectRot extends
        FDraftAspectRot,
        FLineAspectRot,
        FRayAspectRot,
        FSegmentAspectRot,
        FPlaneAspectRot {
}
