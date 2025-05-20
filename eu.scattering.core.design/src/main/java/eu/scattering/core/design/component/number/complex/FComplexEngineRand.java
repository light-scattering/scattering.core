package eu.scattering.core.design.component.number.complex;

import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;

public interface FComplexEngineRand {

    FComplex rndPos(FComplex in, FPairPos2D range);

    FComplex rndPosInCircle(FComplex in, double radius);
    FComplex rndPosOnCircle(FComplex in, double radius);
}
