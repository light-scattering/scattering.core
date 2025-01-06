package eu.scattering.core.design.mutables.geometry.construct.line;

import eu.scattering.core.design.annotations.MutableState;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

public interface FLineFactory {

    FLine getFLine();

    @MutableState
    FLine getRefFLine(FVector refCore);

    //--------------------------------------------------

    default FLine getFLine(FPairPos3D position) {

        return getFLine().set(position);
    }
}
