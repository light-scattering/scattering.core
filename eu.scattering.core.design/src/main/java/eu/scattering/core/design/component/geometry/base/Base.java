package eu.scattering.core.design.component.geometry.base;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.transfer.primitive.FMatrix3x3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import org.json.JSONObject;

public interface Base<T> extends Geometry {

    T set(JSONObject json);

    T applyStateTo(T in);
    T applyStateFrom(T arg);

    boolean isExact(T arg);
    boolean isSimilar(T arg);

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

    T copy();

    //--------------------------------------------------

    @Fragment
    T self();
}
