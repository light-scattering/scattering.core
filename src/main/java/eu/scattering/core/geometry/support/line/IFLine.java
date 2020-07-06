package eu.scattering.core.geometry.support.line;

import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.main.IBaseExtension;
import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.support.ISupport;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IFLine extends IGeometryBase<IFLine>, IDebug<IFLine>, ISupport<IFLine> {

    Consumer<IBaseExtensionAssembly> moveForward(double distance);
    Consumer<IBaseExtensionAssembly> moveBackward(double distance);

    Function<IBaseExtensionAssembly, List<Boolean>> isPartOfRay();
    Function<IBaseExtensionAssembly, List<Boolean>> isPartOfSegment();

    IFPoint getIFPoint(double length);
    Optional<IFPoint> getIFPointAtX(double x);
    Optional<IFPoint> getIFPointAtY(double y);
    Optional<IFPoint> getIFPointAtZ(double z);

    Optional<IFPoint> getCommonIFPoint(IFLine ref);
}
