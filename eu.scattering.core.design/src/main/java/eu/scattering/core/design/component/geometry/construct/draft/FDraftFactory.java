package eu.scattering.core.design.component.geometry.construct.draft;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

public interface FDraftFactory {

    FDraftProducer getFDraftProducer();

    //--------------------------------------------------

    FDraft getFDraft();

    @Modificator
    FDraft getRefFDraft(FVector refOrigin);

    //--------------------------------------------------

    @Modificator
    default FDraft getRefFDraft(Construct<?> construct) {

        return getRefFDraft(construct.getRefOrigin());
    }

    default FDraft getFDraft(FPairPos3D position) {

        return getFDraft().set(position);
    }
}
