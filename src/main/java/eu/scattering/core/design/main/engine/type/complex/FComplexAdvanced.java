package eu.scattering.core.design.main.engine.type.complex;

public interface FComplexAdvanced {

    double getMagnitude();
    FComplex setMagnitude(double magnitude);

    double getPhase();
    FComplex setPhase(double phase);

    FComplex add(FComplex fComplex);
    FComplex add(double re, double im);
    FComplex add(double factor);
    FComplex addRe(double re);
    FComplex addIm(double im);

    FComplex sub(FComplex fComplex);
    FComplex sub(double re, double im);
    FComplex sub(double factor);
    FComplex subRe(double re);
    FComplex subIm(double re);

    FComplex mul(FComplex fComplex);
    FComplex mul(double re, double im);
    FComplex mul(double factor);
    FComplex mulRe(double re);
    FComplex mulIm(double im);

    FComplex div(FComplex fComplex);
    FComplex div(double re, double im);
    FComplex div(double factor);
    FComplex divRe(double re);
    FComplex divIm(double im);

    FComplex pow(int n);
    FComplex[] root(int n);

    FComplex inverse();
    FComplex conjugate();
    FComplex normalize();

    FComplex imprint(FComplex fComplex);

    boolean isZero();
}
