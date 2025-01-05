package eu.scattering.core.design.mutables.geometry.construct.line;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.construct.Construct;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FLine extends Construct<FLine> {

    FPoint getFPoint(double length);

    Optional<FPoint> getFPointAtX(double x);
    Optional<FPoint> getFPointAtY(double y);
    Optional<FPoint> getFPointAtZ(double z);

    Optional<FPoint> getCommonFPoint(FLine ref);

    //--------------------------------------------------
    // Extensions
    //--------------------------------------------------

    void rotate(Geometry geometry, double angle);

    void moveForward(Geometry geometry, double distance);
    void moveBackward(Geometry geometry, double distance);

    List<Boolean> isPartOfRay(Geometry geometry);
    List<Boolean> isPartOfSegment(Geometry geometry);
}
