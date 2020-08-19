package eu.scattering.core.design.main.engine.support.plane;

import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.engine.Engine;
import eu.scattering.core.design.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.support.Support;
import eu.scattering.core.design.main.engine.support.line.FLine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface FPlane extends Engine<FPlane>, Development<FPlane>, Support<FPlane>, Cloneable {

    Function<BaseExtensionAssembly, List<Boolean>> isInHalfSpace();

    boolean isCut(BaseExtensionAssembly assembly);

    Optional<FPoint> getCommonIFPoint(FLine ref);
    Optional<FLine> getCommonIFLine(FPlane ref);

    Object clone();
}

