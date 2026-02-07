package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos3D;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;

import java.util.Optional;

public interface FPlane extends Construct<FPlane> {

    FPlane set(FPairPos3D position);
    FPlane set(FPoint ptBase, FPoint ptA, FPoint ptB);
    FPlane set(FPos3D ptBase, FPos3D ptA, FPos3D ptB);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    double getDistance(double x, double y, double z);
    double getDistance(FPoint arg);
    double getDistance(FPos3D arg);

    FPos3D setDistance(double x, double y, double z, double distance);
    FPos3D setDistance(FPos3D arg, double distance);

    boolean setDistance(FPoint in, double distance);
    boolean setDistance(Geometry in, double distance);

    //--------------------------------------------------

    boolean isSamePlane(FPlane arg);

    boolean isCut(Geometry arg);

    boolean isOnSide(FPoint arg);
    boolean isOnSide(Geometry arg);

    Optional<FPoint> getFPointAtIntersection(FLine arg);
    Optional<FLine> getFLineAtIntersection(FPlane arg);
}

