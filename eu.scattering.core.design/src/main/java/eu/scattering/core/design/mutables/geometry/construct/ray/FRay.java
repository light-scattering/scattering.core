package eu.scattering.core.design.mutables.geometry.construct.ray;

import eu.scattering.core.design.annotations.Intermediate;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.Construct;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

import java.util.List;
import java.util.OptionalDouble;

public interface FRay extends Construct<FRay> {

    FRay set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    FPoint getFPointAtDistance(double length);

    void shiftForward(Geometry geometry, double distance);
    void shiftBackward(Geometry geometry, double distance);

    void setDistance(Geometry geometry, double distance);

    //--------------------------------------------------

    // TODO - Some fields should be empty
    List<OptionalDouble> getAtomicDistance(Geometry geometry);

    @Intermediate
    List<OptionalDouble> getAtomicDistanceP2(Geometry geometry);
}
