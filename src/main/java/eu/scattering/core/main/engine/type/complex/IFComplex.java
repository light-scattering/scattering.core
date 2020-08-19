package eu.scattering.core.main.engine.type.complex;

import eu.scattering.core.main.engine.IEngine;
import eu.scattering.core.main.engine.base.point.IFPoint;

public interface IFComplex extends IFComplexAdvanced, IEngine<IFPoint> {

    IFComplex set(double re, double im);

    double getRe();
    IFComplex setRe(double re);

    double getIm();
    IFComplex setIm(double im);

    Object clone();
}
