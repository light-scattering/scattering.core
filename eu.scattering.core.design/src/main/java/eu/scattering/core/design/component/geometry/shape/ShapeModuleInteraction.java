package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.annotation.Facade;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.List;

public interface ShapeModuleInteraction {

    boolean attachLinear(Shape target);

    boolean attachSpherical(Shape target, double x, double y, double z);
    boolean attachSpherical(Shape target, FPoint anchor);
    boolean attachSpherical(Shape target, FPos3D anchor);

//    double attachCircular(Shape target, FLine axis);

//    double project(Shape target, FVector path);
//    double project(Shape target, FVector path, double distLimit);
//    double project(Iterable<? extends Shape> field, FVector path);
//    double project(Iterable<? extends Shape> field, FVector path, double distLimit);

    double projectFrom(Shape target, FRay path);
    double projectFrom(Shape target, FRay path, double distLimit);
    double projectFrom(Iterable<? extends Shape> field, FRay path);
    double projectFrom(Iterable<? extends Shape> field, FRay path, double distLimit);

//    void getCollisionsLinear(List<Shape> in, Iterable<? extends Shape> field, FVector path);

    void getCollisionsFromLinear(List<Shape> in, Iterable<? extends Shape> field, FRay path);

    void getCollisionsSpherical(List<Shape> in, Iterable<? extends Shape> field, double x, double y, double z);
    void getCollisionsSpherical(List<Shape> in, Iterable<? extends Shape> field, FPoint anchor);
    void getCollisionsSpherical(List<Shape> in, Iterable<? extends Shape> field, FPos3D anchor);

//    void getCollisionsCircular(List<Shape> in, Iterable<? extends Shape> field, FLine axis);

    Shape setRadiusMin(Iterable<? extends Shape> shapes);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    double projectFromDryRun(Shape target, FRay path);
    @Fragment
    double projectFromDryRun(Shape target, FRay path, double distLimit);
    @Fragment
    double projectFromDryRun(Iterable<? extends Shape> field, FRay path);
    @Fragment
    double projectFromDryRun(Iterable<? extends Shape> field, FRay path, double distLimit);

    // -------------------------------------------------------------------------------------------------

    @Facade
    boolean attachLinearWithSphericalCorrection(Shape target, Iterable<? extends Shape> field, int corrections);
}
