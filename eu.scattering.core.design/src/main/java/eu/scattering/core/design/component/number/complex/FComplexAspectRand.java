package eu.scattering.core.design.component.number.complex;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;

public interface FComplexAspectRand {

    FComplex withinRange(FComplex in, FPairPos2D range);

    FComplex intoCircle(FComplex in, double radius);
    FComplex ontoCircle(FComplex in, double radius);
}
