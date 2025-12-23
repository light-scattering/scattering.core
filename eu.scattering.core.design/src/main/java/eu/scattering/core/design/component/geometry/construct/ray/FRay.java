package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;

public interface FRay extends Construct<FRay> {

    FRay set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    FVector toFVector(double length);

    //--------------------------------------------------

    boolean isProjectable(FPoint arg);

    FPoint getFPointAtDistance(double length);

    void shiftForward(FPoint in, double distance);
    void shiftForward(Geometry in, double distance);

    void shiftBackward(FPoint in, double distance);
    void shiftBackward(Geometry in, double distance);

    void setDistance(FPoint in, double distance);
    void setDistance(Geometry in, double distance);

    double getDistance(double x, double y, double z);
    double getDistance(FPoint arg);
}
