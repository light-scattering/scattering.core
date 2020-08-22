package eu.scattering.core.design.main.engine.type.complex;

import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.engine.Engine;

public interface FComplex extends FComplexAdvanced,
        Engine<FComplex>, Development<FComplex>, Cloneable {

    FComplex set(FComplex fComplex);
    FComplex set(double re, double im);

    double getRe();
    FComplex setRe(double re);

    double getIm();
    FComplex setIm(double im);

    Object clone();
}
