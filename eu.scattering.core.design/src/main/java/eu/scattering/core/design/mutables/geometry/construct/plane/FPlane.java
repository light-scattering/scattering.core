package eu.scattering.core.design.mutables.geometry.construct.plane;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.construct.Construct;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface FPlane extends Construct<FPlane> {

    boolean isCut(Geometry assembly);

    Optional<FPoint> getCommonFPoint(FLine ref);
    Optional<FLine> getCommonFLine(FPlane ref);

    //--------------------------------------------------
    // Extensions
    //--------------------------------------------------

    Function<Geometry, List<Boolean>> isInHalfSpace();
}

