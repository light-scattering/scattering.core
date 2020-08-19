package eu.scattering.core.main.engine.extension.line;

import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.main.engine.IEngine;
import eu.scattering.core.dev.IDev;
import eu.scattering.core.main.engine.base.IBaseExtensionAssembly;
import eu.scattering.core.main.engine.base.point.IFPoint;
import eu.scattering.core.main.engine.extension.IExtension;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IFLine extends IEngine<IFLine>, IDev<IFLine>, IExtension<IFLine>, Cloneable {

    Consumer<IBaseExtensionAssembly> moveForward(double distance) throws DirectionException;
    Consumer<IBaseExtensionAssembly> moveBackward(double distance) throws DirectionException;

    Function<IBaseExtensionAssembly, List<Boolean>> isPartOfRay();
    Function<IBaseExtensionAssembly, List<Boolean>> isPartOfSegment();

    IFPoint getIFPoint(double length);
    Optional<IFPoint> getIFPointAtX(double x);
    Optional<IFPoint> getIFPointAtY(double y);
    Optional<IFPoint> getIFPointAtZ(double z);

    Optional<IFPoint> getCommonIFPoint(IFLine ref);

    Object clone();
}
