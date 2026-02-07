package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos3D;
import eu.scattering.core.design.utility.annotation.Facade;
import eu.scattering.core.design.utility.annotation.Fragment;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.line.FLine;

import java.util.List;

public interface ShapeModuleInteraction {

    boolean attachLinear(Shape target);

    boolean attachSpherical(Shape target, double x, double y, double z);
    boolean attachSpherical(Shape target, FPoint anchor);
    boolean attachSpherical(Shape target, FPos3D anchor);

    void getAttachSphericalCollisions(List<Shape> in, Iterable<? extends Shape> field, double x, double y, double z);
    void getAttachSphericalCollisions(List<Shape> in, Iterable<? extends Shape> field, FPoint anchor);
    void getAttachSphericalCollisions(List<Shape> in, Iterable<? extends Shape> field, FPos3D anchor);

//    double attachCircular(Shape target, FLine axis);

    void getAttachCircularCollisions(List<Shape> in, Iterable<? extends Shape> field, FLine axis);

    double project(Shape target, FVector dir);
    double project(Shape target, FVector dir, double distLimit);
    double project(Iterable<? extends Shape> field, FVector dir);
    double project(Iterable<? extends Shape> field, FVector dir, double distLimit);

    void getProjectCollisions(List<Shape> in, Iterable<? extends Shape> field, FVector dir);

    double projectFrom(Shape target, FVector path);
    double projectFrom(Shape target, FVector path, double distLimit);
    double projectFrom(Iterable<? extends Shape> field, FVector path);
    double projectFrom(Iterable<? extends Shape> field, FVector path, double distLimit);

    void getProjectFromCollisions(List<Shape> in, Iterable<? extends Shape> field, FVector path);

    Shape setRadiusMin(Iterable<? extends Shape> shapes);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    double projectFromDryRun(Shape target, FVector path);
    @Fragment
    double projectFromDryRun(Shape target, FVector path, double distLimit);
    @Fragment
    double projectFromDryRun(Iterable<? extends Shape> field, FVector path);
    @Fragment
    double projectFromDryRun(Iterable<? extends Shape> field, FVector path, double distLimit);

    // -------------------------------------------------------------------------------------------------

    @Facade
    boolean attachLinearWithSphericalCorrection(Shape target, Iterable<? extends Shape> field, int corrections);
}
