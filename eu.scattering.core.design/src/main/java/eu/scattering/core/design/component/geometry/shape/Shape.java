package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.transfer.container.buffer.cache.FCache;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.List;

public interface Shape extends Geometry,
        ShapeModuleDimension, ShapeModulePosition, ShapeModuleRelation {

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




    Shape setRadiusMin(Iterable<? extends Shape> shapes);

    void sortByDistCenter(List<? extends Shape> in);
    void sortByDistSpace(List<? extends Shape> in);














    //--------------------------------------------------

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
