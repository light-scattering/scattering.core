package eu.scattering.core.geometry.support.plane;

import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.geometry.main.IGeometryAssembly;
import eu.scattering.core.geometry.support.IGeometrySupport;
import eu.scattering.core.geometry.support.line.IFLine;

import java.util.List;
import java.util.function.Function;

public interface IFPlane  extends IGeometryBase<IFPlane>, IDebug<IFPlane>, IGeometrySupport<IFPlane> {

    Function<IGeometryAssembly, List<Boolean>> isInHalfspace(IFLine.Mode mode);

}

