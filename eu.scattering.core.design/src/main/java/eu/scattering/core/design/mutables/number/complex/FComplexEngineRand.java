package eu.scattering.core.design.mutables.number.complex;

import eu.scattering.core.transfer.containers.position.FPairPos2D.FPairPos2D;

public interface FComplexEngineRand {

    FComplex rndPosition(FComplex origin, FPairPos2D range, FComplex... exclusion);
    FComplex rndPosition(FComplex origin, double radius, FComplex... exclusion);
}
