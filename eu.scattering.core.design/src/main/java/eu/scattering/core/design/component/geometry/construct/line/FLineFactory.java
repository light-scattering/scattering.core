package eu.scattering.core.design.component.geometry.construct.line;

import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos3D;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;

public interface FLineFactory {

    FLineProducer getFLineProducer();

    FLineHelper getFLineHelper();

    //--------------------------------------------------

    FLine getFLine();

    @Modificator
    FLine getRefFLine(FVector refOrigin);

    //--------------------------------------------------

    @Modificator
    default FLine getRefFLine(Construct<?> construct) {

        return getRefFLine(construct.getRefOrigin());
    }

    default FLine getFLine(FPairPos3D position) {

        return getFLine().set(position);
    }
}
