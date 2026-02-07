package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;

public interface FSegmentHelper {

    boolean isPartOf(FVector origin, double x, double y, double z);
    boolean isPartOf(FVector origin, FPoint arg);
    boolean isPartOf(FVector origin, FPos3D arg);
    boolean isPartOf(FVector origin, Geometry arg);

    boolean isPartOf(FVector origin, double x, double y, double z, double epsilon);
    boolean isPartOf(FVector origin, FPoint arg, double epsilon);
    boolean isPartOf(FVector origin, FPos3D arg, double epsilon);
    boolean isPartOf(FVector origin, Geometry arg, double epsilon);

    boolean isProjectable(FVector origin, double x, double y, double z);
    boolean isProjectable(FVector origin, FPoint arg);
    boolean isProjectable(FVector origin, FPos3D arg);

    //--------------------------------------------------

    double getDistance(FVector origin, double x, double y, double z);
    double getDistance(FVector origin, FPoint arg);
    double getDistance(FVector origin, FPos3D arg);

    FPos3D setDistance(FVector origin, double x, double y, double z, double distance);
    FPos3D setDistance(FVector origin, FPos3D arg, double distance);

    boolean setDistance(FVector origin, FPoint in, double distance);
    boolean setDistance(FVector origin, Geometry in, double distance);

    //--------------------------------------------------

    FPos3D project(FVector origin, double x, double y, double z);
    FPos3D project(FVector origin, FPos3D arg);

    boolean project(FVector origin, FPoint in);
    boolean project(FVector origin, Geometry in);

    FPos3D reflect(FVector origin, double x, double y, double z);
    FPos3D reflect(FVector origin, FPos3D arg);

    boolean reflect(FVector origin, FPoint in);
    boolean reflect(FVector origin, Geometry in);
}
