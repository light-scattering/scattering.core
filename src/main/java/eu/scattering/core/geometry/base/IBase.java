package eu.scattering.core.geometry.base;

import eu.scattering.core.geometry.base.point.IFPoint;

public interface IBase<T> extends IBaseExtension<T> {

    T add(IFPoint fPoint);
    T add(double x, double y, double z);
    T add(double factor);
    T addX(double x);
    T addY(double y);
    T addZ(double z);

    T sub(IFPoint fPoint);
    T sub(double x, double y, double z);
    T sub(double factor);
    T subX(double x);
    T subY(double y);
    T subZ(double z);

    T mul(IFPoint fPoint);
    T mul(double x, double y, double z);
    T mul(double factor);
    T mulX(double x);
    T mulY(double y);
    T mulZ(double z);

    T div(IFPoint fPoint);
    T div(double x, double y, double z);
    T div(double factor);
    T divX(double x);
    T divY(double y);
    T divZ(double z);

    T set(T element);
    T imprint(T element);
}
