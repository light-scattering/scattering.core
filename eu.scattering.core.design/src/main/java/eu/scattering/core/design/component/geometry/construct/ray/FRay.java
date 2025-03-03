package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

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
