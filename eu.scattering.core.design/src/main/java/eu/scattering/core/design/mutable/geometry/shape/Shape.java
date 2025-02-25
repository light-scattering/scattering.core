package eu.scattering.core.design.mutable.geometry.shape;

import eu.scattering.core.design.mutable.geometry.Geometry;
import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.container.position.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.position.FPos3DI.FPos3DI;

import java.util.Collection;

public interface Shape<T> extends Geometry {

    boolean contains(FPoint fPoint);
    boolean contains(FPos3D fPos3D);

    boolean intersectsWith(Shape shape);

    void getIntersectingShapes(Collection<Shape> in, Collection<Shape> arg);

    Iterable<FPoint> getDoubleVolumeMesh(double distance);
    Iterable<FPos3DI> getIntegerVolumeMesh(double distance);

    Iterable<FPoint> getDoubleSurfaceMesh(double distance);
    Iterable<FPos3DI> getIntegerSurfaceMesh(double distance);

    double getAlgebraicVolume();
    Shape setAlgebraicVolume(double volume);

    double getAlgebraicSurface();
    Shape setAlgebraicSurface(double surface);

    double getOuterRadius();
    T setOuterRadius(double radius);

    double getInnerRadius();
    T setInnerRadius(double innerRadius);

    FPoint getCenter();
    T setCenter(FPoint position);
}
