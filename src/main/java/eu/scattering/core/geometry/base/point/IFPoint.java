package eu.scattering.core.geometry.base.point;

import eu.scattering.core.geometry.IGeometryAlgebra;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.geometry.IGeometryDebug;

public interface IFPoint extends IFPointAdvanced,
        IGeometryBase<IFPoint>, IGeometryDebug<IFPoint>, IGeometryAlgebra<IFPoint> {

    IFPoint set(double x, double y, double z);

    double getX();
    IFPoint setX(double x);

    double getY();
    IFPoint setY(double y);

    double getZ();
    IFPoint setZ(double z);
}
