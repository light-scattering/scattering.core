package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.shape.sphere.FSphereEngineRand;

public interface ShapeEngineRand extends FSphereEngineRand {

    boolean attachLinear(Shape in, Shape target);
    boolean attachLinear(Shape in, Shape target, Iterable<? extends Shape> shapes, int corrections);

    boolean attachSpherical(Shape in ,Shape target, double x, double y, double z);
    boolean attachSpherical(Shape in ,Shape target, double x, double y, double z, Iterable<? extends Shape> shapes, int corrections);

    boolean attach(Shape in, Shape target, Iterable<? extends Shape> shapes, int corrections);
}
