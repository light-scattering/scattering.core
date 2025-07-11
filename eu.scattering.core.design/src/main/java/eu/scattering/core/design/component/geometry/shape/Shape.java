package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.cache.FCache;
import eu.scattering.core.transfer.container.buffer.layer.FLayer;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.List;

public interface Shape extends Geometry {

    boolean isExact(Shape arg);
    boolean isExactCenter(Shape arg);

    boolean isSimilar(Shape arg);
    boolean isSimilarCenter(Shape arg);

    FPos3D getCenter();
    double getCenterX();
    double getCenterY();
    double getCenterZ();

    Shape setCenter(double x, double y, double z);
    Shape setCenter(FPoint fPoint);
    Shape setCenter(FPos3D fPos3D);
    Shape setCenterX(double x);
    Shape setCenterY(double y);
    Shape setCenterZ(double z);

    double getDistCenter(Shape shape);
    Shape setDistCenter(Shape shape, double dist);

    Shape translate(double x, double y, double z);
    Shape translate(FPoint fPoint);
    Shape translate(FPos3D fPos3D);

    Shape scale(double factor);

    boolean contains(double x, double y, double z);
    boolean contains(FPoint fPoint);
    boolean contains(FPos3D fPos3D);

    boolean touches(Shape shape);
    int touches(Iterable<? extends Shape> shapes);
    int touches(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean overlaps(Shape shape);
    int overlaps(Iterable<? extends Shape> shapes);
    int overlaps(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean encloses(Shape shape);
    int encloses(Iterable<? extends Shape> shapes);
    int encloses(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean intersects(Shape shape);
    int intersects(Iterable<? extends Shape> shapes);
    int intersects(Iterable<? extends Shape> shapes, List<Shape> in);

    double getVolume();
    Shape setVolume(double volume);
    void addVolume(FLayer in);
    void addVolume(FLayer in, Iterable<? extends Shape> shapes);
    void addVolumeArray(FArray in);
    void addVolumeArray(FArray in, Iterable<? extends Shape> shapes);

    double getSurface();
    Shape setSurface(double surface);
    void addSurface(FLayer in);
    void addSurface(FLayer in, Iterable<? extends Shape> shapes);
    void addSurfaceArray(FArray in);
    void addSurfaceArray(FArray in, Iterable<? extends Shape> shapes);

    double getRadius();
    double getInnerRadius();
    Shape setRadius(double radius);
    Shape setInnerRadius(double radius);

    Shape setMinRadius(Iterable<? extends Shape> shapes);

    boolean attachLinear(Shape target);
    boolean attachSpherical(Shape target, double x, double y, double z);

    boolean attach(Shape target, Iterable<? extends Shape> field, int corrections);

    boolean project(FPoint aim, List<FSphere> field);

    void sortByDistance(List<? extends Shape> in);

    // -------------------------------------------------------------------------------------------------

    Shape createCache();

    FCache getCache();
    Shape setCache(FCache cache);

    String getMeta();
    Shape setMeta(String meta);

    double getEpsilon();
    Shape setEpsilon(double epsilon);

    double getDelta();
    Shape setDelta(double delta);

    int getIndex();
    Shape setIndex(int index);

    //--------------------------------------------------

    Shape copy();

    @Fragment
    double getDistCenterP2(Shape shape);

    //--------------------------------------------------

    default boolean attachSpherical(Shape target, FPos3D center) {

        return attachSpherical(target, center.getD0(), center.getD1(), center.getD2());
    }
}
