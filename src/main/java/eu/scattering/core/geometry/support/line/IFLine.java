package eu.scattering.core.geometry.support.line;

import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.main.IBaseExtension;
import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.support.ISupport;

import java.util.Optional;
import java.util.function.Consumer;

public interface IFLine extends IGeometryBase<IFLine>, IDebug<IFLine>, ISupport<IFLine> {

    Consumer<IBaseExtensionAssembly> moveForward(double distance);
    Consumer<IBaseExtensionAssembly> moveBackward(double distance);

    boolean isProjectableOnRay(IBaseExtensionAssembly ref);
    boolean isProjectableOnSegment(IBaseExtensionAssembly ref);

//    boolean isPartOfRay(IBaseExtensionAssembly ref);
//    boolean isPartOfSegment(IBaseExtensionAssembly ref);

    Optional<IFPoint> getIntersectingIFPoint();

}
