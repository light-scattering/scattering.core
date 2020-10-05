package eu.scattering.core.design.main.mutable.geometry.shape;

import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.fixed.position.FPosition;

import java.util.List;

public interface Shape<T> {

    boolean contains(FPoint fPoint);

    boolean intersectsWith(Shape shape);
    Shape[] getIntersectingShapes(Shape... shapes);

    Iterable<FPoint> getVolumeMesh(double distance);
    Iterable<FPoint> getSurfaceMesh(double distance);

    double getVolume(Shape... exclusion);
    double getSurface(Shape... exclusion);

    double getRadius();
    double getInnerRadius();

    Shape scale(double factor);

    FPoint getPosition();
    Shape setPosition(FPoint position);
}
