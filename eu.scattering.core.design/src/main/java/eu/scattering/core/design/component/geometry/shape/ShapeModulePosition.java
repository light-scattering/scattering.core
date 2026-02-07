package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos2D;
import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos3D;
import eu.scattering.core.design.utility.annotation.Fragment;
import eu.scattering.core.design.utility.annotation.Modificator;

import java.util.List;

public interface ShapeModulePosition {

    FPos3D getCenter();

    Shape setCenter(double x, double y, double z);
    Shape setCenter(FPos2D xy, double z);
    Shape setCenter(double x, FPos2D yz);
    Shape setCenter(Shape shape);
    Shape setCenter(FPoint fPoint);
    Shape setCenter(FPos3D fPos3D);

    double getCenterX();
    double getCenterY();
    double getCenterZ();

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

    void sortByDistCenter(List<? extends Shape> in);
    void sortByDistSpace(List<? extends Shape> in);

    Shape scalePosition(double factor);

    //--------------------------------------------------

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
}
