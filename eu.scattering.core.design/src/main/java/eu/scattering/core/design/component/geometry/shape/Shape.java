package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.transfer.container.buffer.FCache.FCache;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
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
    boolean overlaps(Shape shape);
    boolean encloses(Shape shape);
    boolean intersects(Shape shape);

    double getVolume();
    void getVolumeBuffer(FStream3D stream, double delta);
    void getVolumeBuffer(FStream3DI stream, double delta);

    Shape setVolume(double volume);

    double getSurface();
    void getSurfaceBuffer(FStream3D stream, double delta);
    void getSurfaceBuffer(FStream3DI stream, double delta);

    Shape setSurface(double surface);

    double getRadius();
    double getRadiusInner();

    Shape setRadius(double radius);
    Shape setRadiusInner(double radius);
    Shape setRadiusMin(FAssembly<? extends Shape> field, double minCutoff);
    Shape setRadiusMax(FAssembly<? extends Shape> field, double maxCutoff);

    boolean attachLinear(Shape target);
    boolean attachLinear(Shape target, FAssembly<? extends Shape> field, int corrections);

    boolean attachSpherical(Shape target, Shape center);

    boolean project(FPoint aim, List<FSphere> field);

    void getOverlappingShapes(List<Shape> in, List<? extends Shape> field);

    void sortByDistance(List<? extends Shape> in);

    // -------------------------------------------------------------------------------------------------

    FCache getFCache();
    void setFCache(FCache cache);

    String getTag();
    void setTag(String tag);

    double getEpsilon();
    Shape setEpsilon(double epsilon);

    double getDelta();
    Shape setDelta(double delta);

    int getIndex();
    void setIndex(int index);

    //--------------------------------------------------

    @Fragment
    double getDistCenterP2(Shape shape);
}
