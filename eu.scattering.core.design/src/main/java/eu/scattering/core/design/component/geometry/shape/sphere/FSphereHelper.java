package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.transfer.primitive.FPos3D;

public interface FSphereHelper {

    double getVolume(double radius);
    double getSurface(double radius);

    double getVolumeRadius(double volume);
    double getSurfaceRadius(double surface);

    double getVolume(FPos3D posA, FPos3D posB, double rA, double rB);
    double getSurface(FPos3D posA, FPos3D posB, double rA, double rB);

    double getVolumeCommon(FPos3D posA, FPos3D posB, double rA, double rB);
    double getSurfaceCommon(FPos3D posA, FPos3D posB, double rA, double rB);

    double getVolumeRing(double r1, double r2);

    double getRadiusOfGyration(double r);

    boolean intersectsCube(Shape shape, double cSqX, double cSqY, double cSqZ, double size);
}
