package eu.scattering.core.design.mutable.geometry.construct.line;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.mutable.geometry.primitive.vector.FVector;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

public interface FLineFactory {

    FLine getFLine();

    @Modificator
    FLine getRefFLine(FVector refOrigin);

    //--------------------------------------------------

    default FLine getFLine(FPairPos3D position) {

        return getFLine().set(position);
    }
}
