package eu.scattering.core.geometry.support.line;

import eu.scattering.core.exception.DirectionException;
import eu.scattering.core.geometry.IGeometry;
import eu.scattering.core.debug.IDev;
import eu.scattering.core.geometry.base.IBaseExtensionAssembly;
import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.support.ISupport;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IFLine extends IGeometry<IFLine>, IDev<IFLine>, ISupport<IFLine> {

    Consumer<IBaseExtensionAssembly> moveForward(double distance) throws DirectionException;
    Consumer<IBaseExtensionAssembly> moveBackward(double distance) throws DirectionException;

    Function<IBaseExtensionAssembly, List<Boolean>> isPartOfRay();
    Function<IBaseExtensionAssembly, List<Boolean>> isPartOfSegment();

    IFPoint getIFPoint(double length);
    Optional<IFPoint> getIFPointAtX(double x);
    Optional<IFPoint> getIFPointAtY(double y);
    Optional<IFPoint> getIFPointAtZ(double z);

    Optional<IFPoint> getCommonIFPoint(IFLine ref);
}
