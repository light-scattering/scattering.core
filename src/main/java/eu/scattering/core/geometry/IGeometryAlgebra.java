package eu.scattering.core.geometry;

import eu.scattering.core.geometry.base.point.IFPoint;

import java.util.List;

public interface IGeometryAlgebra<T> {

    List<IFPoint> getIFPoints();
    T self();

    T add(IFPoint fPoint);
    T add(double x, double y, double z);

    T addX(double x);
    T addY(double y);
    T addZ(double z);

    T sub(IFPoint fPoint);
    T sub(double x, double y, double z);

    T subX(double x);
    T subY(double y);
    T subZ(double z);

    T mul(IFPoint fPoint);
    T mul(double x, double y, double z);

    T mulX(double x);
    T mulY(double y);
    T mulZ(double z);

    T div(IFPoint fPoint);
    T div(double x, double y, double z);

    T divX(double x);
    T divY(double y);
    T divZ(double z);

    T scale(double scaleFactor);

}
