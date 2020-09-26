package eu.scattering.core.test.design.main.mutable.number.complex;

public interface FComplexFactory {

    FComplex getFComplex();

    default FComplex getFComplex(double re, double im) {

        return getFComplex().set(re, im);
    }
}
