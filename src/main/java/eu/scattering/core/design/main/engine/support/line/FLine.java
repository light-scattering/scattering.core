package eu.scattering.core.design.main.engine.support.line;

import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.design.main.engine.Engine;
import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.support.Support;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FLine extends Engine<FLine>, Development<FLine>, Support<FLine>, Cloneable {

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
