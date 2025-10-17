package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.Optional;

public interface FPlane extends Construct<FPlane> {

    FPlane set(FPairPos3D position);
    FPlane set(FPoint ptBase, FPoint ptA, FPoint ptB);
    FPlane set(FPos3D ptBase, FPos3D ptA, FPos3D ptB);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isSamePlane(FPlane arg);

    boolean isCut(Geometry arg);

    boolean isOnSide(FPoint arg);
    boolean isOnSide(Geometry arg);

    Optional<FPoint> getFPointAtIntersection(FLine arg);
    Optional<FLine> getFLineAtIntersection(FPlane arg);

    void setDistance(FPoint in, double distance);
    void setDistance(Geometry in, double distance);

    double getDistance(FPoint arg);
}

