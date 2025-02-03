package eu.scattering.core.design.mutable.geometry.construct.line;

import eu.scattering.core.design.mutable.geometry.Geometry;
import eu.scattering.core.design.mutable.geometry.construct.Construct;
import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

import java.util.List;
import java.util.Optional;

public interface FLine extends Construct<FLine> {

    FLine set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isSameLine(FLine arg);

    Optional<FPoint> getFPointAtX(double x);
    Optional<FPoint> getFPointAtY(double y);
    Optional<FPoint> getFPointAtZ(double z);

    Optional<FPoint> getFPointAtIntersection(FLine arg);

    void setDistance(Geometry geometry, double distance);

    //--------------------------------------------------

    List<Double> getAtomicDistance(Geometry geometry);
}
