package eu.scattering.core.logic.main.engine.geometry;

import eu.scattering.core.logic.main.engine.base.point.FPoint;

import java.util.List;

public interface Geometry<T> {

    boolean isPartOf(FPoint fPoint);

    List<FPoint> getMesh();

    double getVolume();

    double getVolume(List<Geometry> shapes);

    double getSurface();

    double getSurface(List<Geometry> shapes);

    double getRadiusOuter();

    double getRadiusInner();

    List<Geometry> getIntersections(List<Geometry> shapes);

    List<Geometry> getCollisions(FPoint direction, List<Geometry> shapes);

    T project(FPoint direction, List<Geometry> shapes);
}
