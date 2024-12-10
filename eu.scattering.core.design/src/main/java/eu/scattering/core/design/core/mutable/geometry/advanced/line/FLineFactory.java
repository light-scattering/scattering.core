package eu.scattering.core.design.core.mutable.geometry.advanced.line;

import eu.scattering.core.design.core.mutable.geometry.simple.vector.FVector;

public interface FLineFactory {

    FLine getFLine();

    default FLine getFLine(FVector fVector) {

        return getFLine().setOriginRef(fVector);
    }
}
