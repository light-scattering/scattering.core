package eu.scattering.core.design.component.geometry.construct.draft;

import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;

public interface FDraft extends Construct<FDraft> {

    FDraft set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    FRay asFRay();
    FLine asFLine();
    FPlane asFPlane();
    FSegment asFSegment();

    //--------------------------------------------------

    @Modificator
    default FVector asFVector() {

        return getRefOrigin();
    }
}
