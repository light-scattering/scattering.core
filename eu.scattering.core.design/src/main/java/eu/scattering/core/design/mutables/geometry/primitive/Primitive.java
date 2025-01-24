package eu.scattering.core.design.mutables.geometry.primitive;

import eu.scattering.core.design.mutables.Mutable;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;

public interface Primitive<T> extends Geometry, Mutable<T> {

    T add(FPoint arg);
    T add(double x, double y, double z);
    T add(double factor);
    T addX(double x);
    T addY(double y);
    T addZ(double z);

    T sub(FPoint arg);
    T sub(double x, double y, double z);
    T sub(double factor);
    T subX(double x);
    T subY(double y);
    T subZ(double z);

    T mul(FPoint arg);
    T mul(double x, double y, double z);
    T mul(double factor);
    T mulX(double x);
    T mulY(double y);
    T mulZ(double z);

    T div(FPoint arg);
    T div(double x, double y, double z);
    T div(double factor);
    T divX(double x);
    T divY(double y);
    T divZ(double z);

    T applyStateTo(T arg);
    T applyStateFrom(T arg);
}
