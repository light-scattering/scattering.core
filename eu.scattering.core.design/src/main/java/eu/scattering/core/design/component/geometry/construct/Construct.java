package eu.scattering.core.design.component.geometry.construct;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.vector.FVector;

public interface Construct<T> extends Geometry, Component<T> {

    @Modificator
    FVector getRefOrigin();
    @Modificator
    T setRefOrigin(FVector refOrigin);

    //--------------------------------------------------

    boolean isPartOf(Geometry geometry);

    void project(Geometry geometry);
    void reflect(Geometry geometry);
}
