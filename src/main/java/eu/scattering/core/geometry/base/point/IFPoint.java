package eu.scattering.core.geometry.base.point;

import eu.scattering.core.geometry.base.IBase;
import eu.scattering.core.geometry.IGeometry;
import eu.scattering.core.debug.IDev;

public interface IFPoint extends IFPointAdvanced,
        IGeometry<IFPoint>, IBase<IFPoint>, IDev<IFPoint>, Cloneable {

    IFPoint set(double x, double y, double z);

    double getX();
    IFPoint setX(double x);

    double getY();
    IFPoint setY(double y);

    double getZ();
    IFPoint setZ(double z);

    Object clone();
}
