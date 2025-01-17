package eu.scattering.core.design.mutables.number.complex;

import eu.scattering.core.transfer.containers.position.FPos2D.FPos2D;

public interface FComplexFactory {

    FComplex getFComplex();

    FComplex getFComplex(double re, double im);

    //--------------------------------------------------

    default FComplex getFComplex(FPos2D origin) {

        return getFComplex().set(origin);
    }
}
