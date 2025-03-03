package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.List;
import java.util.Optional;

public interface FPlane extends Construct<FPlane> {

    FPlane set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isSamePlane(FPlane ref);

    boolean isCut(Geometry geometry);
    boolean isOnSide(Geometry geometry);

    Optional<FPoint> getFPointAtIntersection(FLine ref);
    Optional<FVector> getFLineOriginAtIntersection(FPlane ref);

    void setDistance(Geometry geometry, double distance);

    //--------------------------------------------------

    List<Double> getAtomicDistance(Geometry geometry);
}

