package eu.scattering.core.design.main.mutable.geometry.shape;

import eu.scattering.core.design.main.mutable.geometry.Geometry;
import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.fixed.position.FPosition;

import java.util.List;

public interface Shape<T> extends Geometry {

    boolean contains(FPoint fPoint);

    boolean intersectsStronglyWith(Shape shape);
    boolean intersectsLooselyWith(Shape shape);

    Shape[] getStronglyIntersectingShapes(Shape... shapes);
    Shape[] getLooselyIntersectingShapes(Shape... shapes);

    Iterable<FPoint> getDoubleVolumeMesh(double distance);
    Iterable<FPosition> getIntegerVolumeMesh(double distance);

    Iterable<FPoint> getDoubleSurfaceMesh(double distance);
    Iterable<FPosition> getIntegerSurfaceMesh(double distance);

    double getVolume(Shape... exclusion);
    double getExactVolume(Shape... exclusion);
    double getApproximateVolume(Shape... exclusion);

    double getSurface(Shape... exclusion);
    double getExactSurface(Shape... exclusion);
    double getApproximateSurface(Shape... exclusion);

    double getOuterRadius();
    double getOuterRadiusP2();
    T setOuterRadius(double radius);

    double getInnerRadius();
    double getInnerRadiusP2();
    T setInnerRadius(double innerRadius);

    FPoint getCenter();
    T setCenter(FPoint position);
}
