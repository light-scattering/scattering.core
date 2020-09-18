package eu.scattering.core.design.main.mutable.geometry.extension.line;

import eu.scattering.core.design.main.mutable.Mutable;
import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.mutable.geometry.Geometry;
import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.mutable.geometry.extension.Extension;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FLine extends Mutable<FLine>, Extension<FLine>, Development<FLine> {

    Consumer<Geometry> moveForward(double distance);
    Consumer<Geometry> moveBackward(double distance);

    Function<Geometry, List<Boolean>> isPartOfRay();
    Function<Geometry, List<Boolean>> isPartOfSegment();

    Consumer<Geometry> rotate(double angle);

    FPoint getFPoint(double length);
    Optional<FPoint> getFPointAtX(double x);
    Optional<FPoint> getFPointAtY(double y);
    Optional<FPoint> getFPointAtZ(double z);

    Optional<FPoint> getCommonFPoint(FLine ref);
}
