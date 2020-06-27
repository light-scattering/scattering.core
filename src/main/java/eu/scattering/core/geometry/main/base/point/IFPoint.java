package eu.scattering.core.geometry.main.base.point;

import eu.scattering.core.geometry.main.IGeometryAlgebra;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.debug.IDebug;

public interface IFPoint extends IFPointAdvanced,
        IGeometryBase<IFPoint>, IDebug<IFPoint>, IGeometryAlgebra<IFPoint> {

    IFPoint set(double x, double y, double z);

    double getX();
    IFPoint setX(double x);

    double getY();
    IFPoint setY(double y);

    double getZ();
    IFPoint setZ(double z);

}
