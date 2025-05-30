package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
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

    T applyStateTo(Construct<?> in);
    T applyStateFrom(Construct<?> arg);

    boolean isExact(T arg);
    boolean isSimilar(T arg);

    boolean isPartOf(FPoint arg);
    boolean isPartOf(FPoint arg, double epsilon);

    boolean isPartOf(Geometry arg);
    boolean isPartOf(Geometry arg, double epsilon);

    void project(FPoint in);
    void project(Geometry in);

    void reflect(FPoint in);
    void reflect(Geometry in);

    T copy();

    //--------------------------------------------------

    @Fragment
    T self();
}
