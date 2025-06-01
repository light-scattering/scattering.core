package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.draft.FDraftEngineRot;
import eu.scattering.core.design.component.geometry.construct.line.FLineEngineRot;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneEngineRot;
import eu.scattering.core.design.component.geometry.construct.ray.FRayEngineRot;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentEngineRot;

public interface ConstructEngineRot extends
        FDraftEngineRot,
        FLineEngineRot,
        FRayEngineRot,
        FSegmentEngineRot,
        FPlaneEngineRot {
}
