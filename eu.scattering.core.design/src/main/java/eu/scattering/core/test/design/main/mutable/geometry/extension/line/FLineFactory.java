package eu.scattering.core.test.design.main.mutable.geometry.extension.line;

import eu.scattering.core.test.design.main.mutable.geometry.base.vector.FVector;

public interface FLineFactory {

    FLine getFLine();

    default FLine getFLine(FVector fVector) {

        return getFLine().setOriginRef(fVector);
    }
}
