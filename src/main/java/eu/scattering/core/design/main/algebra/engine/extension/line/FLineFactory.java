package eu.scattering.core.design.main.algebra.engine.extension.line;

import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;

public interface FLineFactory {

    FLine getFLine();

    default FLine getFLine(FVector fVector) {

        return getFLine().setOriginRef(fVector);
    }
}
