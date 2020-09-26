package eu.scattering.core.test.design.main.mutable.geometry.shape;

import eu.scattering.core.test.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.test.design.main.fixed.position.FPosition;

import java.util.List;

public interface Shape<T> {

    boolean isPartOf(FPoint fPoint);

    List<FPosition> getMesh();

    double getVolume();

    double getVolume(List<Shape> shapes);

    double getSurface();

    double getSurface(List<Shape> shapes);

    double getRadiusOuter();

    double getRadiusInner();

    List<Shape> getIntersections(List<Shape> shapes);

    List<Shape> getCollisions(FPoint direction, List<Shape> shapes);

    T project(FPoint direction, List<Shape> shapes);
}
