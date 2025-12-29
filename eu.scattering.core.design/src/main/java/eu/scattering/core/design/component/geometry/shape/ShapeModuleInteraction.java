package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.annotation.Facade;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.List;

public interface ShapeModuleInteraction {

    boolean attachLinear(Shape target);

    boolean attachSpherical(Shape target, double x, double y, double z);
    boolean attachSpherical(Shape target, FPoint center);
    boolean attachSpherical(Shape target, FPos3D center);

    double projectWithOrigin(Shape target, FRay ray);
    double projectWithOrigin(Iterable<? extends Shape> field, FRay ray);

    double projectWithOrigin(Shape target, FRay ray, double limit);
    double projectWithOrigin(Iterable<? extends Shape> field, FRay ray, double limit);

    void getCollisionListWithOriginLinear(List<Shape> in, Iterable<? extends Shape> field, FRay ray);

    void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, double x, double y, double z);
    void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, FPoint center);
    void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, FPos3D center);

    Shape setRadiusMin(Iterable<? extends Shape> shapes);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    double projectWithOriginDryRun(Shape target, FRay ray);
    @Fragment
    double projectWithOriginDryRun(Iterable<? extends Shape> field, FRay ray);
    @Fragment
    double projectWithOriginDryRun(Shape target, FRay ray, double limit);
    @Fragment
    double projectWithOriginDryRun(Iterable<? extends Shape> field, FRay ray, double limit);

    // -------------------------------------------------------------------------------------------------

    @Facade
    boolean attachLinearWithSphericalCorrection(Shape target, Iterable<? extends Shape> field, int corrections);
}
