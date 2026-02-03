package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.Optional;

public interface FPlaneHelper {

    void setOrigin(FVector in, FPoint ptBase, FPoint ptA, FPoint ptB);

    //--------------------------------------------------

    boolean isPartOf(FVector origin, double x, double y, double z);
    boolean isPartOf(FVector origin, FPoint arg);
    boolean isPartOf(FVector origin, FPos3D arg);
    boolean isPartOf(FVector origin, Geometry arg);

    boolean isPartOf(FVector origin, double x, double y, double z, double epsilon);
    boolean isPartOf(FVector origin, FPoint arg, double epsilon);
    boolean isPartOf(FVector origin, FPos3D arg, double epsilon);
    boolean isPartOf(FVector origin, Geometry arg, double epsilon);

    //--------------------------------------------------

    double getDistance(FVector origin, double x, double y, double z);
    double getDistance(FVector origin, FPoint arg);
    double getDistance(FVector origin, FPos3D arg);

    FPos3D setDistance(FVector origin, double x, double y, double z, double distance);
    FPos3D setDistance(FVector origin, FPos3D arg, double distance);

    void setDistance(FVector origin, FPoint in, double distance);
    void setDistance(FVector origin, Geometry in, double distance);

    //--------------------------------------------------

    FPos3D project(FVector origin, double x, double y, double z);
    FPos3D project(FVector origin, FPos3D arg);

    void project(FVector origin, FPoint in);
    void project(FVector origin, Geometry in);

    FPos3D reflect(FVector origin, double x, double y, double z);
    FPos3D reflect(FVector origin, FPos3D arg);

    void reflect(FVector origin, FPoint in);
    void reflect(FVector origin, Geometry in);

    //--------------------------------------------------

    boolean isSamePlane(FVector origin, FVector arg);

    boolean isCut(FVector origin, Geometry arg);

    boolean isOnSide(FVector origin, FPoint arg);
    boolean isOnSide(FVector origin, Geometry arg);

    Optional<FPoint> getFPointAtIntersection(FVector origin, FVector arg);
    Optional<FVector> getFLineAtIntersection(FVector origin, FVector arg);
}

// http://geomalgorithms.com/a05-_intersect-1.html
// https://opentextbc.ca/calculusv3openstax/chapter/equations-of-lines-and-planes-in-space/
