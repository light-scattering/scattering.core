package eu.scattering.core.geometry.d0;

import eu.scattering.core.geometry.IBaseAlgebra;
import eu.scattering.core.geometry.IBaseObject;

public interface IFPoint extends IPointAdvanced, IBaseAlgebra<IFPoint> , IBaseObject<IFPoint> {

    IFPoint set(IFPoint fPoint);
    IFPoint set(double x, double y, double z);

    double getX();
    IFPoint setX(double x);

    double getY();
    IFPoint setY(double y);

    double getZ();
    IFPoint setZ(double z);

}
