package eu.scattering.core.design.mutables.geometry.construct.line;

import eu.scattering.core.design.annotations.IntermediateResults;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.Construct;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

import java.util.List;
import java.util.Optional;

public interface FLine extends Construct<FLine> {

    FLine set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isCollinear(FLine ref);

    Optional<FPoint> getFPointAtX(double x);
    Optional<FPoint> getFPointAtY(double y);
    Optional<FPoint> getFPointAtZ(double z);

    Optional<FPoint> getFPointAtIntersection(FLine ref);

    List<Double> getDistance(Geometry geometry);
    void setDistance(Geometry geometry, double distance);

    //--------------------------------------------------

    @IntermediateResults
    List<Double> getDistanceP2(Geometry geometry);
}
