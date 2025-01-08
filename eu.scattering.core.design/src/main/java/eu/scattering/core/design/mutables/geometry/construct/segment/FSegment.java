package eu.scattering.core.design.mutables.geometry.construct.segment;

import eu.scattering.core.design.annotations.IntermediateResults;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.Construct;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

import java.util.List;
import java.util.OptionalDouble;

public interface FSegment extends Construct<FSegment> {

    FSegment set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    List<OptionalDouble> getDistance(Geometry geometry);
    void setDistance(Geometry geometry, double distance);

    //--------------------------------------------------

    @IntermediateResults
    List<OptionalDouble> getDistanceP2(Geometry geometry);
}
