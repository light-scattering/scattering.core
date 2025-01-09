package eu.scattering.core.design.mutables.geometry.construct.segment;

import eu.scattering.core.design.annotations.Intermediate;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.Construct;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

import java.util.List;
import java.util.OptionalDouble;

public interface FSegment extends Construct<FSegment> {

    FSegment set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    void setDistance(Geometry geometry, double distance);

    //--------------------------------------------------

    List<OptionalDouble> getAtomicDistance(Geometry geometry);

    @Intermediate
    List<OptionalDouble> getAtomicDistanceP2(Geometry geometry);
}
