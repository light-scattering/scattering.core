package eu.scattering.core.geometry.base.point;

import eu.scattering.core.geometry.base.IBaseAlgebra;
import eu.scattering.core.geometry.ICoreFeatures;

public interface IFPoint extends IFPointAdvanced, IBaseAlgebra<IFPoint>, ICoreFeatures<IFPoint> {

    IFPoint set(IFPoint fPoint);

    IFPoint set(double x, double y, double z);

    double getX();
    IFPoint setX(double x);

    double getY();
    IFPoint setY(double y);

    double getZ();
    IFPoint setZ(double z);
}
