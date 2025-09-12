package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

public interface ShapeModuleComposition {

    boolean contains(double x, double y, double z);
    boolean contains(FPoint fPoint);
    boolean contains(FPos3D fPos3D);

    int locate(double x, double y, double z);
    int locate(FPoint fPoint);
    int locate(FPos3D fPos3D);

    //--------------------------------------------------

    @Fragment
    boolean containsWithSurface(double x, double y, double z, int layer);
    @Fragment
    boolean containsWithSurface(double x, double y, double z);
}
