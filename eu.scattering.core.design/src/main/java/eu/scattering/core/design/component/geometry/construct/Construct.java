package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONObject;

public interface Construct<T> extends Geometry {

    @Modificator
    FVector getRefOrigin();
    @Modificator
    T setRefOrigin(FVector refOrigin);

    //--------------------------------------------------

    T set(JSONObject json);

    T applyStateTo(Construct<?> in);
    T applyStateFrom(Construct<?> arg);

    boolean isExact(T arg);
    boolean isSimilar(T arg);

    boolean isPartOf(FPoint arg);
    boolean isPartOf(FPoint arg, double epsilon);

    boolean isPartOf(Geometry arg);
    boolean isPartOf(Geometry arg, double epsilon);

    FPos3D project(double x, double y, double z);
    boolean project(FPoint in);
    boolean project(Geometry in);

    FPos3D reflect(double x, double y, double z);
    boolean reflect(FPoint in);
    boolean reflect(Geometry in);

    T copy();

    //--------------------------------------------------

    @Fragment
    T self();
}
