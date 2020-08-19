package eu.scattering.core.main.engine.geometry;

import eu.scattering.core.main.engine.base.point.IFPoint;

import java.util.List;

public interface IGeometry<T> {

    boolean isPartOf(IFPoint fPoint);

    List<IFPoint> getMesh();

    double getVolume();

    double getVolume(List<IGeometry> shapes);

    double getSurface();

    double getSurface(List<IGeometry> shapes);

    double getRadiusOuter();

    double getRadiusInner();

    List<IGeometry> getIntersections(List<IGeometry> shapes);

    List<IGeometry> getCollisions(IFPoint direction, List<IGeometry> shapes);

    T project(IFPoint direction, List<IGeometry> shapes);
}
