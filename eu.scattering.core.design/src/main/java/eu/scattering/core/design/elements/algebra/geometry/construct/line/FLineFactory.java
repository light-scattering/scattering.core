package eu.scattering.core.design.elements.algebra.geometry.construct.line;

import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;

public interface FLineFactory {

    FLine getFLine();

    default FLine getFLine(FVector fVector) {

        return getFLine().setOriginRef(fVector);
    }
}
