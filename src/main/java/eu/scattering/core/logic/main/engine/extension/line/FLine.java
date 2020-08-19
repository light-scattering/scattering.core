package eu.scattering.core.logic.main.engine.extension.line;

import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.logic.main.engine.Engine;
import eu.scattering.core.logic.dev.Dev;
import eu.scattering.core.logic.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.logic.main.engine.base.point.FPoint;
import eu.scattering.core.logic.main.engine.extension.Extension;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FLine extends Engine<FLine>, Dev<FLine>, Extension<FLine>, Cloneable {

    Consumer<BaseExtensionAssembly> moveForward(double distance) throws DirectionException;
    Consumer<BaseExtensionAssembly> moveBackward(double distance) throws DirectionException;

    Function<BaseExtensionAssembly, List<Boolean>> isPartOfRay();
    Function<BaseExtensionAssembly, List<Boolean>> isPartOfSegment();

    FPoint getIFPoint(double length);
    Optional<FPoint> getIFPointAtX(double x);
    Optional<FPoint> getIFPointAtY(double y);
    Optional<FPoint> getIFPointAtZ(double z);

    Optional<FPoint> getCommonIFPoint(FLine ref);

    Object clone();
}
