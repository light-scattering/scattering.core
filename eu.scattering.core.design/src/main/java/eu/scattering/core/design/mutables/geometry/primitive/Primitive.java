package eu.scattering.core.design.mutables.geometry.primitive;

import eu.scattering.core.design.mutables.Mutable;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.containers.grid.FMatrix3x3D.FMatrix3x3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public interface Primitive<T> extends Geometry, Mutable<T> {

    T addXYZ(FPoint arg);
    T addXYZ(FPos3D arg);
    T addXYZ(double x, double y, double z);
    T addFactor(double factor);
    T addX(double x);
    T addY(double y);
    T addZ(double z);

    T subXYZ(FPoint arg);
    T subXYZ(FPos3D arg);
    T subXYZ(double x, double y, double z);
    T subFactor(double factor);
    T subX(double x);
    T subY(double y);
    T subZ(double z);

    T mulXYZ(FPoint arg);
    T mulXYZ(FPos3D arg);
    T mulXYZ(double x, double y, double z);
    T mulFactor(double factor);
    T mulX(double x);
    T mulY(double y);
    T mulZ(double z);

    T divXYZ(FPoint arg);
    T divXYZ(FPos3D arg);
    T divXYZ(double x, double y, double z);
    T divFactor(double factor);
    T divX(double x);
    T divY(double y);
    T divZ(double z);

    T mul(FMatrix3x3D arg);

    T applyStateTo(T arg);
    T applyStateFrom(T arg);
}
