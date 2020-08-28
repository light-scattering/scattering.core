package eu.scattering.core.design.main.algebra.engine.extension.plane;

import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.algebra.Algebra;
import eu.scattering.core.design.main.algebra.engine.Engine;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.extension.Extension;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface FPlane extends Algebra<FPlane>, Extension<FPlane>, Development<FPlane>, Cloneable {

    Function<Engine, List<Boolean>> isInHalfSpace();

    boolean isCut(Engine assembly);

    Optional<FPoint> getCommonFPoint(FLine ref);
    Optional<FLine> getCommonFLine(FPlane ref);

    Object clone();
}

