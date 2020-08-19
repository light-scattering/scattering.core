package eu.scattering.core.logic.main.engine.type.complex;

import eu.scattering.core.logic.main.engine.Engine;
import eu.scattering.core.logic.main.engine.base.point.FPoint;

public interface FComplex extends FComplexAdvanced, Engine<FPoint> {

    FComplex set(double re, double im);

    double getRe();
    FComplex setRe(double re);

    double getIm();
    FComplex setIm(double im);

    Object clone();
}
