package eu.scattering.core.logic.main.engine.extension.plane;

import eu.scattering.core.logic.dev.Dev;
import eu.scattering.core.logic.main.engine.Engine;
import eu.scattering.core.logic.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.logic.main.engine.base.point.FPoint;
import eu.scattering.core.logic.main.engine.extension.Extension;
import eu.scattering.core.logic.main.engine.extension.line.FLine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface FPlane extends Engine<FPlane>, Dev<FPlane>, Extension<FPlane>, Cloneable {

    Function<BaseExtensionAssembly, List<Boolean>> isInHalfSpace();

    boolean isCut(BaseExtensionAssembly assembly);

    Optional<FPoint> getCommonIFPoint(FLine ref);
    Optional<FLine> getCommonIFLine(FPlane ref);

    Object clone();
}

