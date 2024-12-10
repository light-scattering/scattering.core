package eu.scattering.core.design.core.mutable.geometry.advanced.line;

import eu.scattering.core.design.core.mutable.Mutable;
import eu.scattering.core.design.debug.Debug;
import eu.scattering.core.design.core.mutable.geometry.Geometry;
import eu.scattering.core.design.core.mutable.geometry.simple.point.FPoint;
import eu.scattering.core.design.core.mutable.geometry.advanced.Advanced;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FLine extends Mutable<FLine>, Advanced<FLine>, Debug<FLine> {

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
