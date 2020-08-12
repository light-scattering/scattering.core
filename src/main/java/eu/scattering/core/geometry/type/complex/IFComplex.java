package eu.scattering.core.geometry.type.complex;

import eu.scattering.core.debug.IDev;
import eu.scattering.core.geometry.IGeometry;
import eu.scattering.core.geometry.base.point.IFPoint;

public interface IFComplex extends IFComplexAdvanced, IGeometry<IFPoint> {

    IFComplex set(double re, double im);

    double getRe();
    IFComplex setRe(double re);

    double getIm();
    IFComplex setIm(double im);

    Object clone();
}
