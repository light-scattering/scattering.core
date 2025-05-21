package eu.scattering.core.design.component.geometry.construct.line;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

public interface FLineFactory {

    FLine getFLine();

    @Modificator
    FLine getRefFLine(FVector refOrigin);

    //--------------------------------------------------

    default FLine getLine(FVector refOrigin) {

        return getRefFLine(refOrigin.copy());
    }

    default FLine getFLine(Construct<?> construct) {

        return getRefFLine(construct.getRefOrigin().copy());
    }

    default FLine getFLine(FPairPos3D position) {

        return getFLine().set(position);
    }

    @Modificator
    default FLine getRefFLine(Construct<?> construct) {

        return getRefFLine(construct.getRefOrigin());
    }
}
