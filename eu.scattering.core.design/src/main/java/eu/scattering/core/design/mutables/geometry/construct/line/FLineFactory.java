package eu.scattering.core.design.mutables.geometry.construct.line;

import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;

public interface FLineFactory {

    FLine getFLine();

    default FLine getFLine(FVector fVector) {

        return getFLine().setOriginRef(fVector);
    }
}
