package eu.scattering.core.geometry.support.plane;

import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.support.ISupport;

import java.util.List;
import java.util.function.Function;

public interface IFPlane  extends IGeometryBase<IFPlane>, IDebug<IFPlane>, ISupport<IFPlane> {

    Function<IBaseExtensionAssembly, List<Boolean>> isInHalfSpace();

    boolean isCutting(IBaseExtensionAssembly assembly);

}

