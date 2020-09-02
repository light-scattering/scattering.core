package eu.scattering.core.design.main.algebra.engine.extension.line;

import eu.scattering.core.design.main.algebra.Algebra;
import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.algebra.engine.Engine;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.Extension;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FLine extends Algebra<FLine>, Extension<FLine>, Development<FLine>, Cloneable {

    Consumer<Engine> moveForward(double distance);
    Consumer<Engine> moveBackward(double distance);

    Function<Engine, List<Boolean>> isPartOfRay();
    Function<Engine, List<Boolean>> isPartOfSegment();

//    Consumer<Engine> rotate(double angle);

    FPoint getFPoint(double length);
    Optional<FPoint> getFPointAtX(double x);
    Optional<FPoint> getFPointAtY(double y);
    Optional<FPoint> getFPointAtZ(double z);

    Optional<FPoint> getCommonFPoint(FLine ref);

    Object clone();
}
