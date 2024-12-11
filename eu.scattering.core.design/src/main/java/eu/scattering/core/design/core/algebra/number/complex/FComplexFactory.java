package eu.scattering.core.design.core.algebra.number.complex;

public interface FComplexFactory {

    FComplex getFComplex();

    default FComplex getFComplex(double re, double im) {

        return getFComplex().set(re, im);
    }
}
