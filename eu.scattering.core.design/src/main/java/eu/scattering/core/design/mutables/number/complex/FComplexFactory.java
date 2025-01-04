package eu.scattering.core.design.mutables.algebra.number.complex;

import eu.scattering.core.transfer.containers.position.FPos2D.FPos2D;

public interface FComplexFactory {

    FComplex getFComplex();

    //--------------------------------------------------

    default FComplex getFComplex(double re, double im) {

        return getFComplex().set(re, im);
    }

    //--------------------------------------------------

    default FComplex getFComplex(FPos2D origin) {

        return getFComplex().set(origin);
    }
}
