package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;

public interface FSegment extends Construct<FSegment> {

    FSegment set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isProjectable(FPoint arg);

    void setDistance(FPoint in, double distance);
    void setDistance(Geometry in, double distance);

    double getDistance(double x, double y, double z);
    double getDistance(FPoint arg);
}
