package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.draft.FDraftEngineProto;
import eu.scattering.core.design.component.geometry.construct.line.FLineEngineProto;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneEngineProto;
import eu.scattering.core.design.component.geometry.construct.ray.FRayEngineProto;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentEngineProt;

public interface ConstructEngineProto extends
        FDraftEngineProto,
        FLineEngineProto,
        FRayEngineProto,
        FSegmentEngineProt,
        FPlaneEngineProto {
}
