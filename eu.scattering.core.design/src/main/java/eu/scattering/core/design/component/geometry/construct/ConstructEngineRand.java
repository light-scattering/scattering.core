package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.draft.FDraftEngineRand;
import eu.scattering.core.design.component.geometry.construct.line.FLineEngineRand;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneEngineRand;
import eu.scattering.core.design.component.geometry.construct.ray.FRayEngineRand;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentEngineRand;

public interface ConstructEngineRand extends
        FDraftEngineRand,
        FLineEngineRand,
        FRayEngineRand,
        FSegmentEngineRand,
        FPlaneEngineRand {
}
