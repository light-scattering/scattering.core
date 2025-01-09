package eu.scattering.core.design.mutables.geometry.construct;

import eu.scattering.core.design.annotations.Mutable;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;

import java.util.List;

public interface Construct<T> extends Geometry, eu.scattering.core.design.mutables.Mutable<T> {

    @Mutable
    FVector getRefOrigin();
    @Mutable
    T setRefOrigin(FVector refOrigin);

    //--------------------------------------------------

    void project(Geometry geometry);
    void reflect(Geometry geometry);

    List<Boolean> isPartOf(Geometry geometry);
}
