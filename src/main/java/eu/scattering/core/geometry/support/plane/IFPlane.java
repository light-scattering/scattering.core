package eu.scattering.core.geometry.support.plane;

import eu.scattering.core.debug.IDev;
import eu.scattering.core.geometry.IGeometry;
import eu.scattering.core.geometry.base.IBaseExtensionAssembly;
import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.support.ISupport;
import eu.scattering.core.geometry.support.line.IFLine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface IFPlane  extends IGeometry<IFPlane>, IDev<IFPlane>, ISupport<IFPlane>, Cloneable {

    Function<IBaseExtensionAssembly, List<Boolean>> isInHalfSpace();

    boolean isCut(IBaseExtensionAssembly assembly);

    Optional<IFPoint> getCommonIFPoint(IFLine ref);
    Optional<IFLine> getCommonIFLine(IFPlane ref);

    Object clone();
}

