package eu.scattering.core.design.component.number.complex;

import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public interface FComplexFactory {

    FComplexProducer getFComplexProducer();

    //--------------------------------------------------

    FComplex getFComplex();

    //--------------------------------------------------

    default FComplex getFComplex(double re, double im) {

        return getFComplex().set(re, im);
    }

    default FComplex getFComplex(double re) {

        return getFComplex().setRe(re);
    }

    //--------------------------------------------------

    default FComplex getFComplex(FPos2D position) {

        return getFComplex().applyStateFrom(position);
    }
}
