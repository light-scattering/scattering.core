package eu.scattering.core.geometry.base.point;

import eu.scattering.core.geometry.IGeometryAlgebra;
import eu.scattering.core.geometry.IGeometryBase;

public interface IFPoint extends IFPointAdvanced, IGeometryAlgebra<IFPoint>, IGeometryBase<IFPoint> {

    IFPoint set(IFPoint fPoint);

    IFPoint set(double x, double y, double z);

    double getX();
    IFPoint setX(double x);

    double getY();
    IFPoint setY(double y);

    double getZ();
    IFPoint setZ(double z);
}
