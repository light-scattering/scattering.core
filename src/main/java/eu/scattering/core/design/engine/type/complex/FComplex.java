package eu.scattering.core.design.engine.type.complex;

import eu.scattering.core.design.engine.Engine;
import eu.scattering.core.design.engine.base.point.FPoint;

public interface FComplex extends FComplexAdvanced,
        Engine<FPoint> {

    FComplex set(double re, double im);

    double getRe();
    FComplex setRe(double re);

    double getIm();
    FComplex setIm(double im);

    Object clone();
}
