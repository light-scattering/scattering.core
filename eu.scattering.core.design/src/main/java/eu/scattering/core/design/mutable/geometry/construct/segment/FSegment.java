package eu.scattering.core.design.mutable.geometry.construct.segment;

import eu.scattering.core.design.mutable.geometry.Geometry;
import eu.scattering.core.design.mutable.geometry.construct.Construct;
import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;

import java.util.List;

public interface FSegment extends Construct<FSegment> {

    FSegment set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    //--------------------------------------------------

    boolean isProjectable(FPoint arg);

    void setDistance(Geometry geometry, double distance);

    //--------------------------------------------------

    List<Double> getAtomicDistance(Geometry geometry);
}
