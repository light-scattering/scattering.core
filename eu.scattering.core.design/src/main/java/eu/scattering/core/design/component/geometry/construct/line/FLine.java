package eu.scattering.core.design.component.geometry.construct.line;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos3D;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;

import java.util.Optional;

public interface FLine extends Construct<FLine> {

    FLine set(FPairPos3D position);

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

    boolean isSameLine(FLine arg);

    Optional<FPoint> getFPointAtX(double x);
    Optional<FPoint> getFPointAtY(double y);
    Optional<FPoint> getFPointAtZ(double z);

    Optional<FPoint> getFPointAtIntersection(FLine arg);
}
