package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereEngineRand;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

public interface ShapeEngineRand extends FSphereEngineRand {

    boolean attachLinear(Shape in, Shape target);
    boolean attachLinear(Shape in, Shape target, Iterable<? extends Shape> field, int corrections);

    boolean attachSpherical(Shape in, Shape target, double x, double y, double z);
    boolean attachSpherical(Shape in, Shape target, FPoint center);
    boolean attachSpherical(Shape in, Shape target, FPos3D center);

    boolean attachSpherical(Shape in, Shape target, double x, double y, double z, Iterable<? extends Shape> field, int corrections);
    boolean attachSpherical(Shape in, Shape target, FPoint center, Iterable<? extends Shape> field, int corrections);
    boolean attachSpherical(Shape in, Shape target, FPos3D center, Iterable<? extends Shape> field, int corrections);

    boolean attachLinearAndSpherical(Shape in, Shape target, Iterable<? extends Shape> field, int corrections);

    boolean project(Shape in, Shape range, Iterable<? extends Shape> field, int corrections);

    //--------------------------------------------------

    boolean attachLinear2D(Shape in, Shape target);
    boolean attachLinear2D(Shape in, Shape target, Iterable<? extends Shape> field, int corrections);
}
