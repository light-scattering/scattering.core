package eu.scattering.core.design.component.number.complex;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;

public interface FComplexAspectRand {

    FComplex inRange(FComplex in, FPairPos2D range);

    FComplex inCircle(FComplex in, double radius);
    FComplex onCircle(FComplex in, double radius);
}
