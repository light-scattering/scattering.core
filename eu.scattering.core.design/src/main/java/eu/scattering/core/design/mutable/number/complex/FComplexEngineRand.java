package eu.scattering.core.design.mutable.number.complex;

import eu.scattering.core.transfer.container.position.FPairPos2D.FPairPos2D;

public interface FComplexEngineRand {

    FComplex rndPos(FComplex in, FPairPos2D range, FComplex... exclusion);
    FComplex rndPos(FComplex in, double radius, FComplex... exclusion);
}
