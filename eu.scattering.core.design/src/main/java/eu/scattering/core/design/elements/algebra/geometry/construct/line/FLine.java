package eu.scattering.core.design.elements.algebra.geometry.construct.line;

import eu.scattering.core.design.elements.algebra.geometry.Geometry;
import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.construct.Construct;

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

    Consumer<Geometry> rotate(double angle);

    Consumer<Geometry> moveForward(double distance);
    Consumer<Geometry> moveBackward(double distance);

    Function<Geometry, List<Boolean>> isPartOfRay();
    Function<Geometry, List<Boolean>> isPartOfSegment();
}
