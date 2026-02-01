package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;

public interface FRay extends Construct<FRay> {

    FRay set(FPairPos3D position);

    FPairPos3D toFPairPos3D();

    FVector toFVector(double length);

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

    //--------------------------------------------------

    FPos3D shiftForward(double x, double y, double z, double distance);
    FPos3D shiftForward(FPos3D arg, double distance);

    void shiftForward(FPoint in, double distance);
    void shiftForward(Geometry in, double distance);

    FPos3D shiftBackward(double x, double y, double z, double distance);
    FPos3D shiftBackward(FPos3D arg, double distance);

    void shiftBackward(FPoint in, double distance);
    void shiftBackward(Geometry in, double distance);

    //--------------------------------------------------

    FPoint getFPointAtLength(double length);
    FPos3D getFPos3DAtLength(double length);

    //-------------------------------------------------- RELOCATE

    boolean isPartOf(double x, double y, double z);
    boolean isPartOf(FPos3D arg);
    boolean isPartOf(double x, double y, double z, double epsilon);
    boolean isPartOf(FPos3D arg, double epsilon);
    FPos3D project(FPos3D arg);
    FPos3D reflect(FPos3D arg);
}
