package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.cache.FCache;
import eu.scattering.core.transfer.container.buffer.layer.FLayerCounter;
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
    Shape setCenter(Shape shape);
    Shape setCenter(FPoint fPoint);
    Shape setCenter(FPos3D fPos3D);

    Shape setCenterX(double x);
    Shape setCenterY(double y);
    Shape setCenterZ(double z);

    double getDistCenter(double x, double y, double z);
    double getDistCenter(Shape shape);
    double getDistCenter(FPoint fPoint);
    double getDistCenter(FPos3D fPos3D);

    Shape setDistCenter(double x, double y, double z, double dist);
    Shape setDistCenter(Shape shape, double dist);
    Shape setDistCenter(FPoint fPoint, double dist);
    Shape setDistCenter(FPos3D fPos3D, double dist);

    Shape translate(double x, double y, double z);
    Shape translate(FPoint fPoint);
    Shape translate(FPos3D fPos3D);

    Shape scaleSize(double factor);
    Shape scalePosition(double factor);

    boolean contains(double x, double y, double z);
    boolean contains(FPoint fPoint);
    boolean contains(FPos3D fPos3D);

    int locate(double x, double y, double z);
    int locate(FPoint fPoint);
    int locate(FPos3D fPos3D);

    double getVolumeAlgebraic();

    void fillVolumeLayer(FLayerCounter in);
    void fillVolumeLayer(FLayerCounter in, Iterable<? extends Shape> shapes);

    void fillVolumeOverlapLayer(FLayerCounter in);
    void fillVolumeOverlapLayer(FLayerCounter in, Iterable<? extends Shape> shapes);

    void fillVolumeArray(FArray in);
    void fillVolumeArray(FArray in, Iterable<? extends Shape> shapes);

    double getSurfaceAlgebraic();
    double getSurfaceDiscrete();

    void fillSurfaceLayer(FLayerCounter in);
    void fillSurfaceLayer(FLayerCounter in, Iterable<? extends Shape> shapes);

    void fillSurfaceOverlapLayer(FLayerCounter in, Iterable<? extends Shape> shapes);

    void fillSurfaceArray(FArray in);
    void fillSurfaceArray(FArray in, Iterable<? extends Shape> shapes);

    double getRadius();
    Shape setRadius(double radius);

    double getInnerRadius();
    Shape setInnerRadius(double radius);

    Shape setRadiusMin(Iterable<? extends Shape> shapes);

    void sortByDistCenter(List<? extends Shape> in);
    void sortByDistSpace(List<? extends Shape> in);

    // -------------------------------------------------------------------------------------------------

    boolean repels(Shape shape);
    int repels(Iterable<? extends Shape> shapes);
    int repels(Iterable<? extends Shape> shapes, List<Shape> in);

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

    boolean touchesOrRepels(Shape shape);
    int touchesOrRepels(Iterable<? extends Shape> shapes);
    int touchesOrRepels(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean touchesOrOverlaps(Shape shape);
    int touchesOrOverlaps(Iterable<? extends Shape> shapes);
    int touchesOrOverlaps(Iterable<? extends Shape> shapes, List<Shape> in);

    // -------------------------------------------------------------------------------------------------

    boolean attachLinear(Shape target);

    boolean attachSpherical(Shape target, double x, double y, double z);
    boolean attachSpherical(Shape target, FPoint center);
    boolean attachSpherical(Shape target, FPos3D center);

    boolean attachLinearAndSpherical(Shape target, Iterable<? extends Shape> field, int corrections);

    boolean project(Shape target, FRay ray);
    boolean project(Iterable<? extends Shape> field, FRay ray);

    void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, double x, double y, double z);
    void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, FPoint center);
    void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, FPos3D center);

    void getCollisionListDirectional(List<Shape> in, Iterable<? extends Shape> field, FRay ray);

    // -------------------------------------------------------------------------------------------------

    Shape applyCoatingFrom(Shape shape);

    Shape removeCoating();

    Shape addCoat(double width);
    Shape addCoat(double... width);

    Shape addCoatInternal(double width);
    Shape addCoatInternal(double... width);

    int getCoatCount();

    double getCoatWidth(int index);
    double getCoatWidthTotal();

    void setCoatWidth(int index, double width);

    double getCoatVolume(int index);
    double getCoatVolumeTotal();

    int getLayerCount();

    double getLayerWidthRemaining(int index);

    double getLayerVolume(int index);

    // -------------------------------------------------------------------------------------------------

    FCache getFCache();
    Shape setFCache(FCache cache);
    Shape resetFCache();

    double getEpsilon();
    Shape setEpsilon(double epsilon);
    Shape resetEpsilon();

    double getDelta();
    Shape setDelta(double delta);
    Shape resetDelta();

    double getIndex();
    Shape setIndex(double index);
    Shape resetIndex();

    String getTag();
    Shape setTag(String tag);
    Shape resetTag();

    //--------------------------------------------------

    Shape copy();

    @Modificator
    FPoint getRefCenter();
    @Modificator
    Shape setRefCenter(FPoint refCenter);

    @Fragment
    double getDistCenterP2(double x, double y, double z);
    @Fragment
    double getDistCenterP2(FPoint fPoint);
    @Fragment
    double getDistCenterP2(FPos3D fPos3D);
    @Fragment
    double getDistCenterP2(Shape shape);

    @Fragment
    boolean containsWithSurface(double x, double y, double z, int layer);
    @Fragment
    boolean containsWithSurface(double x, double y, double z);
}
