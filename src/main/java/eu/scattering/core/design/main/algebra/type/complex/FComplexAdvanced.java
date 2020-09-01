package eu.scattering.core.design.main.algebra.type.complex;

public interface FComplexAdvanced {

    boolean isExact(double re, double im);
    boolean isSimilar(double re, double im);

    double getPhase();
    FComplex setPhase(double phase);

    FComplex add(double re, double im);
    FComplex addRe(double re);
    FComplex addIm(double im);

    FComplex sub(double re, double im);
    FComplex subRe(double re);
    FComplex subIm(double re);

    FComplex mul(double re, double im);
    FComplex mulRe(double re);
    FComplex mulIm(double im);

    FComplex div(double re, double im);
    FComplex divRe(double re);
    FComplex divIm(double im);

    FComplex[] root(int n);
}
