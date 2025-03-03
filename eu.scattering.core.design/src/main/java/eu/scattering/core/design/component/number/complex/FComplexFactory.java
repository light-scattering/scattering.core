package eu.scattering.core.design.component.number.complex;

import eu.scattering.core.transfer.container.position.FPos2D.FPos2D;

public interface FComplexFactory {

    FComplex getFComplex();

    FComplex getFComplex(double re, double im);

    //--------------------------------------------------

    default FComplex getFComplex(double re) {

        return getFComplex(re, 0);
    }

    //--------------------------------------------------

    default FComplex getFComplex(FPos2D origin) {

        return getFComplex(origin.getD0(), origin.getD1());
    }
}
