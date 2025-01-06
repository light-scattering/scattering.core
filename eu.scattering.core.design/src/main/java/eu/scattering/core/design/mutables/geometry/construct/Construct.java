package eu.scattering.core.design.mutables.geometry.construct;

import eu.scattering.core.design.annotations.IntermediateResults;
import eu.scattering.core.design.annotations.MutableState;
import eu.scattering.core.design.mutables.Mutable;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;

import java.util.List;

public interface Construct<T> extends Geometry, Mutable<T> {

    @MutableState
    FVector getRefOrigin();
    @MutableState
    T setRefOrigin(FVector refOrigin);

    //--------------------------------------------------

    void project(Geometry geometry);
    void reflect(Geometry geometry);

    List<Double> getDistance(Geometry geometry);
    void setDistance(Geometry geometry, double distance);

    List<Boolean> isPartOf(Geometry geometry);

    //--------------------------------------------------

    @IntermediateResults
    List<Double> getDistanceP2(Geometry geometry);
}
