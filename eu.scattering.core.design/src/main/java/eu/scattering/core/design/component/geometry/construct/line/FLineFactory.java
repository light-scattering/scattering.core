package eu.scattering.core.design.component.geometry.construct.line;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;

public interface FLineFactory {

    FLineProducer getFLineProducer();

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
