package eu.scattering.core.geometry.shape;

import eu.scattering.core.geometry.base.point.IFPoint;

import java.util.List;

public interface IShape<T> {

    boolean isPartOf(IFPoint fPoint);

    List<IFPoint> getMesh();

    double getVolume();

    double getVolume(List<IShape> shapes);

    double getSurface();

    double getSurface(List<IShape> shapes);

    double getRadiusOuter();

    double getRadiusInner();

    List<IShape> getIntersections(List<IShape> shapes);

    List<IShape> getCollisions(IFPoint direction, List<IShape> shapes);

    T project(IFPoint direction, List<IShape> shapes);
}
