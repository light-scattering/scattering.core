package eu.scattering.core.design.engine.support.line;

import eu.scattering.core.design.engine.Engine;
import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.engine.base.BaseExtensionAssembly;
import eu.scattering.core.design.engine.base.point.FPoint;
import eu.scattering.core.design.engine.support.Support;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FLine extends Engine<FLine>, Development<FLine>, Support<FLine>, Cloneable {

    Consumer<BaseExtensionAssembly> moveForward(double distance) throws IllegalStateException;
    Consumer<BaseExtensionAssembly> moveBackward(double distance) throws IllegalStateException;

    Function<BaseExtensionAssembly, List<Boolean>> isPartOfRay();
    Function<BaseExtensionAssembly, List<Boolean>> isPartOfSegment();

    FPoint getFPoint(double length);
    Optional<FPoint> getFPointAtX(double x);
    Optional<FPoint> getFPointAtY(double y);
    Optional<FPoint> getFPointAtZ(double z);

    Optional<FPoint> getCommonFPoint(FLine ref);

    Object clone();
}
