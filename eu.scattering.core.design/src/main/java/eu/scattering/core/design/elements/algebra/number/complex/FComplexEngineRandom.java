package eu.scattering.core.design.elements.algebra.number.complex;

import eu.scattering.core.transfer.containers.position.FPairPos2D.FPairPos2D;

public interface FComplexEngineRandom {

    FComplex rndPosition(FComplex origin, FPairPos2D range, FComplex... exclusion);
    FComplex rndPosition(FComplex origin, double radius, FComplex... exclusion);
}
