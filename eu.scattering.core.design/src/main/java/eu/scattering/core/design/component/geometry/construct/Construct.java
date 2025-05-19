package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;

public interface Construct<T> extends Geometry, Component<T> {

    @Modificator
    FVector getRefOrigin();
    @Modificator
    T setRefOrigin(FVector refOrigin);

    //--------------------------------------------------

    boolean isPartOf(FPoint arg);
    boolean isPartOf(FPoint arg, double epsilon);

    boolean isPartOf(Geometry arg);
    boolean isPartOf(Geometry arg, double epsilon);

    void project(FPoint in);
    void project(Geometry in);

    void reflect(FPoint in);
    void reflect(Geometry in);
}
