package eu.scattering.core.design.main.engine.support.plane;

import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.engine.Engine;
import eu.scattering.core.design.main.engine.Disassemble;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.support.Support;
import eu.scattering.core.design.main.engine.support.line.FLine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface FPlane extends Engine<FPlane>, Support<FPlane>, Development<FPlane>, Cloneable {

    Function<Disassemble, List<Boolean>> isInHalfSpace();

    boolean isCut(Disassemble assembly);

    Optional<FPoint> getCommonFPoint(FLine ref);
    Optional<FLine> getCommonFLine(FPlane ref);

    Object clone();
}

