package eu.scattering.core.design.main.algebra.engine.shape;

import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.container.position.FPosition;

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
