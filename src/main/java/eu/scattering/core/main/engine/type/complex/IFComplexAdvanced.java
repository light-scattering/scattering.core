package eu.scattering.core.main.engine.type.complex;

public interface IFComplexAdvanced {

    IFComplex setPolarCoordinates(double magnitude, double phase);

    double getMagnitude();
    double setMagnitude(double magnitude);

    double getPhase();
    double setPhase(double phase);

    IFComplex add(IFComplex fComplex);
    IFComplex add(double re, double im);
    IFComplex add(double factor);
    IFComplex addRe(double re);
    IFComplex addIm(double im);

    IFComplex sub(IFComplex fComplex);
    IFComplex sub(double re, double im);
    IFComplex sub(double factor);
    IFComplex subRe(double re);
    IFComplex subIm(double re);

    IFComplex mul(IFComplex fComplex);
    IFComplex mul(double re, double im);
    IFComplex mul(double factor);
    IFComplex mulRe(double re);
    IFComplex mulIm(double im);

    IFComplex div(IFComplex dComplex);
    IFComplex div(double re, double im);
    IFComplex div(double factor);
    IFComplex divRe(double re);
    IFComplex divIm(double im);

    IFComplex pow(int n);
    IFComplex root(int n);

    IFComplex conjugate();

    IFComplex imprint(IFComplex fComplex);
}
