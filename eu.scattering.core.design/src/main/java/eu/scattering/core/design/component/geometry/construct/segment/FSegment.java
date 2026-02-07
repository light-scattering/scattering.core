package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos3D;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;

public interface FSegment extends Construct<FSegment> {

    FSegment set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isProjectable(double x, double y, double z);
    boolean isProjectable(FPoint arg);
    boolean isProjectable(FPos3D arg);

    //--------------------------------------------------

    double getDistance(double x, double y, double z);
    double getDistance(FPoint arg);
    double getDistance(FPos3D arg);

    FPos3D setDistance(double x, double y, double z, double distance);
    FPos3D setDistance(FPos3D arg, double distance);

    boolean setDistance(FPoint in, double distance);
    boolean setDistance(Geometry in, double distance);
}
