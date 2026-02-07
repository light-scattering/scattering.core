package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;
import eu.scattering.core.design.utility.annotation.Fragment;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import org.json.JSONObject;

public interface Construct<T> extends Geometry {

    @Modificator
    FVector getRefOrigin();
    @Modificator
    T setRefOrigin(FVector refOrigin);

    //--------------------------------------------------

    T set(JSONObject json);
    T set(Construct<?> arg);

    T applyStateTo(Construct<?> in);

    boolean isExact(T arg);
    boolean isSimilar(T arg);

    boolean isPartOf(double x, double y, double z);
    boolean isPartOf(FPoint arg);
    boolean isPartOf(FPos3D arg);
    boolean isPartOf(Geometry arg);

    boolean isPartOf(double x, double y, double z, double epsilon);
    boolean isPartOf(FPoint arg, double epsilon);
    boolean isPartOf(FPos3D arg, double epsilon);
    boolean isPartOf(Geometry arg, double epsilon);

    FPos3D project(double x, double y, double z);
    FPos3D project(FPos3D arg);

    boolean project(FPoint in);
    boolean project(Geometry in);

    FPos3D reflect(double x, double y, double z);
    FPos3D reflect(FPos3D arg);

    boolean reflect(FPoint in);
    boolean reflect(Geometry in);

    T copy();

    //--------------------------------------------------

    @Fragment
    T self();
}
