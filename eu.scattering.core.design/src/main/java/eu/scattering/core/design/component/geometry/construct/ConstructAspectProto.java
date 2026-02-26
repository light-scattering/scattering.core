package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.draft.FDraftAspectProto;
import eu.scattering.core.design.component.geometry.construct.line.FLineAspectProto;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneAspectProto;
import eu.scattering.core.design.component.geometry.construct.ray.FRayAspectProto;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentAspectProt;

public interface ConstructAspectProto extends
        FDraftAspectProto,
        FLineAspectProto,
        FRayAspectProto,
        FSegmentAspectProt,
        FPlaneAspectProto {
}
