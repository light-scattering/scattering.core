package eu.scattering.core.geometry.type.complex;

import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.geometry.main.base.point.IFPoint;

public interface IFComplex extends IFComplexAdvanced,
        IGeometryBase<IFPoint>, IDebug<IFComplex> {

    IFComplex set(double re, double im);

    double getRe();
    IFComplex setRe(double re);

    double getIm();
    IFComplex setIm(double im);

    Object clone();
}
