package eu.scattering.core.design.core.mutable.geometry.advanced.plane;

import eu.scattering.core.design.debug.Debug;
import eu.scattering.core.design.core.mutable.Mutable;
import eu.scattering.core.design.core.mutable.geometry.Geometry;
import eu.scattering.core.design.core.mutable.geometry.simple.point.FPoint;
import eu.scattering.core.design.core.mutable.geometry.advanced.Advanced;
import eu.scattering.core.design.core.mutable.geometry.advanced.line.FLine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface FPlane extends Mutable<FPlane>, Advanced<FPlane>, Debug<FPlane> {

    boolean isCut(Geometry assembly);

    Optional<FPoint> getCommonFPoint(FLine ref);
    Optional<FLine> getCommonFLine(FPlane ref);

    //--------------------------------------------------
    // Extensions
    //--------------------------------------------------

    Function<Geometry, List<Boolean>> isInHalfSpace();
}

