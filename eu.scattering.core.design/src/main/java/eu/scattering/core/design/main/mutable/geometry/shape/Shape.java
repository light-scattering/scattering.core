package eu.scattering.core.design.main.mutable.geometry.shape;

import eu.scattering.core.design.main.mutable.geometry.Geometry;
import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.fixed.position.FPosition;

import java.util.List;

public interface Shape<T> extends Geometry {

    boolean contains(FPoint fPoint);

    boolean intersectsWith(Shape shape);
    Shape[] getIntersectingShapes(Shape... shapes);

    Iterable<FPoint> getVolumeMesh(double distance);
    Iterable<FPoint> getSurfaceMesh(double distance);

    double getVolume(Shape... exclusion);
    double getSurface(Shape... exclusion);

    double getRadius();
    double getRadiusP2();
    T setRadius(double radius);

    double getInnerRadius();
    double getInnerRadiusP2();
    T setInnerRadius(double innerRadius);

    T setPosition(FPoint position);
}
