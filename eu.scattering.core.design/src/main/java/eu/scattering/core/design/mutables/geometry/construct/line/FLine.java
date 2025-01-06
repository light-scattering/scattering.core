package eu.scattering.core.design.mutables.geometry.construct.line;

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

    FPoint getFPointAtDistance(double length);

    Optional<FPoint> getFPointAtX(double x);
    Optional<FPoint> getFPointAtY(double y);
    Optional<FPoint> getFPointAtZ(double z);

    Optional<FPoint> getCommonFPoint(FLine ref);

    boolean isSameLine(FLine ref);

    void shiftForward(Geometry geometry, double distance);
    void shiftBackward(Geometry geometry, double distance);

    List<Boolean> isPartOfRay(Geometry geometry);
    List<Boolean> isPartOfSegment(Geometry geometry);
}
