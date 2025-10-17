package eu.scattering.core.design.component.number.complex;

import eu.scattering.core.design.transfer.primitive.FPairPos2D;

public interface FComplexEngineRand {

    FComplex inRange(FComplex in, FPairPos2D range);

    FComplex inCircle(FComplex in, double radius);
    FComplex onCircle(FComplex in, double radius);
}
