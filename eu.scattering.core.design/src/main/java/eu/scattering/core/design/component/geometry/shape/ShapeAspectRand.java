package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereAspectRand;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface ShapeAspectRand extends FSphereAspectRand {

    boolean attachLinear(Shape in, Shape target);
    boolean attachLinear(Shape in, Shape target, Iterable<? extends Shape> field, int corrections);

    boolean attachSpherical(Shape in, Shape target, double x, double y, double z);
    boolean attachSpherical(Shape in, Shape target, FPoint center);
    boolean attachSpherical(Shape in, Shape target, FPos3D center);

    boolean attachSpherical(Shape in, Shape target, double x, double y, double z, Iterable<? extends Shape> field, int corrections);
    boolean attachSpherical(Shape in, Shape target, FPoint center, Iterable<? extends Shape> field, int corrections);
    boolean attachSpherical(Shape in, Shape target, FPos3D center, Iterable<? extends Shape> field, int corrections);

    boolean attachLinearAndSpherical(Shape in, Shape target, Iterable<? extends Shape> field, int corrections);

    double project(Shape in, FPos3D center, double radius, Iterable<? extends Shape> field, int corrections);

    //--------------------------------------------------

    boolean attachLinearOnPlane(Shape in, Shape target);
    boolean attachLinearOnPlane(Shape in, Shape target, Iterable<? extends Shape> field, int corrections);

    boolean attachSphericalOnPlane(Shape in, Shape target, double x, double y, double z);

    boolean attachSphericalOnPlane(Shape in, Shape target, double x, double y, double z, Iterable<? extends Shape> field, int corrections);
    boolean attachSphericalOnPlane(Shape in, Shape target, FPoint center, Iterable<? extends Shape> field, int corrections);

    double projectOnPlane(Shape in, FPos3D center, double radius, Iterable<? extends Shape> field, int corrections);
}
