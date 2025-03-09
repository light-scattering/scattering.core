package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.List;
import java.util.Optional;

public interface FPlane extends Construct<FPlane> {

    FPlane set(FPairPos3D position);
    // FPlane set(FPoint ptA, FPoint ptB, FPoint ptC);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isSamePlane(FPlane arg);

    boolean isCut(Geometry arg);
    boolean isOnSide(Geometry arg);

    Optional<FPoint> getFPointAtIntersection(FLine arg);
    Optional<FLine> getFLineAtIntersection(FPlane arg);

    void setDistance(FPoint in, double distance);
    void setDistance(Geometry in, double distance);

    double getDistance(FPoint arg);
    List<Double> getAtomicDistance(Geometry arg);
}

