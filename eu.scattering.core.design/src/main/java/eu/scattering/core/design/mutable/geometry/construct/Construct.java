package eu.scattering.core.design.mutable.geometry.construct;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.mutable.geometry.Geometry;
import eu.scattering.core.design.mutable.geometry.primitive.vector.FVector;

public interface Construct<T> extends Geometry, eu.scattering.core.design.mutable.Mutable<T> {

    @Modificator
    FVector getRefOrigin();
    @Modificator
    T setRefOrigin(FVector refOrigin);

    //--------------------------------------------------

    boolean isPartOf(Geometry geometry);

    void project(Geometry geometry);
    void reflect(Geometry geometry);
}
