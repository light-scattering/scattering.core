package eu.scattering.core.test.design.main.mutable.geometry.extension.plane;

import eu.scattering.core.test.design.development.Development;
import eu.scattering.core.test.design.main.mutable.Mutable;
import eu.scattering.core.test.design.main.mutable.geometry.Geometry;
import eu.scattering.core.test.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.test.design.main.mutable.geometry.extension.Extension;
import eu.scattering.core.test.design.main.mutable.geometry.extension.line.FLine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface FPlane extends Mutable<FPlane>, Extension<FPlane>, Development<FPlane>{

    Function<Geometry, List<Boolean>> isInHalfSpace();

    boolean isCut(Geometry assembly);

    Optional<FPoint> getCommonFPoint(FLine ref);
    Optional<FLine> getCommonFLine(FPlane ref);
}

