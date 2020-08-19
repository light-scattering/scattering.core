package eu.scattering.core.main.engine.extension.plane;

import eu.scattering.core.dev.IDev;
import eu.scattering.core.main.engine.IEngine;
import eu.scattering.core.main.engine.base.IBaseExtensionAssembly;
import eu.scattering.core.main.engine.base.point.IFPoint;
import eu.scattering.core.main.engine.extension.IExtension;
import eu.scattering.core.main.engine.extension.line.IFLine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface IFPlane  extends IEngine<IFPlane>, IDev<IFPlane>, IExtension<IFPlane>, Cloneable {

    Function<IBaseExtensionAssembly, List<Boolean>> isInHalfSpace();

    boolean isCut(IBaseExtensionAssembly assembly);

    Optional<IFPoint> getCommonIFPoint(IFLine ref);
    Optional<IFLine> getCommonIFLine(IFPlane ref);

    Object clone();
}

