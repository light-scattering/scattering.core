package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.util.annotation.Facade;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.List;

public interface ShapeModuleAggregation {

    boolean attachLinear(Shape target);

    boolean attachSpherical(Shape target, double x, double y, double z);
    boolean attachSpherical(Shape target, FPoint center);
    boolean attachSpherical(Shape target, FPos3D center);

    boolean project(Shape target, FRay ray);
    boolean project(Iterable<? extends Shape> field, FRay ray);

    void getCollisionListLinear(List<Shape> in, Iterable<? extends Shape> field, FRay ray);

    void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, double x, double y, double z);
    void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, FPoint center);
    void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, FPos3D center);

    Shape setRadiusMin(Iterable<? extends Shape> shapes);

    // -------------------------------------------------------------------------------------------------

    @Facade
    boolean attachLinearWithSphericalCorrection(Shape target, Iterable<? extends Shape> field, int corrections);
}
