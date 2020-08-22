package eu.scattering.core.design.main.algebra.type.complex;

import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.algebra.Algebra;

public interface FComplex extends FComplexAdvanced,
        Algebra<FComplex>, Development<FComplex>, Cloneable {

    FComplex set(FComplex fComplex);
    FComplex set(double re, double im);

    double getRe();
    FComplex setRe(double re);

    double getIm();
    FComplex setIm(double im);

    Object clone();
}
