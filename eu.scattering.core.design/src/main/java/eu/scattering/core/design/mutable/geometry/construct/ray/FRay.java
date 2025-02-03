package eu.scattering.core.design.mutable.geometry.construct.ray;

import eu.scattering.core.design.mutable.geometry.Geometry;
import eu.scattering.core.design.mutable.geometry.construct.Construct;
import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

import java.util.List;

public interface FRay extends Construct<FRay> {

    FRay set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isProjectable(FPoint arg);

    FPoint getFPointAtDistance(double length);

    void shiftForward(Geometry geometry, double distance);
    void shiftBackward(Geometry geometry, double distance);

    void setDistance(Geometry geometry, double distance);

    //--------------------------------------------------

    List<Double> getAtomicDistance(Geometry geometry);
}
