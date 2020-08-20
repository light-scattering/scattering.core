package eu.scattering.core.design.main.engine.support.line;

import eu.scattering.core.design.main.engine.Engine;
import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.engine.base.BaseComposite;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.support.Support;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FLine extends Engine<FLine>, Support<FLine>, Development<FLine>, Cloneable {

    Consumer<BaseComposite> moveForward(double distance);
    Consumer<BaseComposite> moveBackward(double distance);

    Function<BaseComposite, List<Boolean>> isPartOfRay();
    Function<BaseComposite, List<Boolean>> isPartOfSegment();

    FPoint getFPoint(double length);
    Optional<FPoint> getFPointAtX(double x);
    Optional<FPoint> getFPointAtY(double y);
    Optional<FPoint> getFPointAtZ(double z);

    Optional<FPoint> getCommonFPoint(FLine ref);

    Object clone();
}
