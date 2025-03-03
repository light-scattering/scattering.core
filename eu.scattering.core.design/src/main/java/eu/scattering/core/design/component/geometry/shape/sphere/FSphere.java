package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.List;

public interface FSphere extends Shape<FSphere> {

    double getRadius();
    FSphere setRadius(double radius);

//    boolean setMaxRadius(List<FSphere> field, double limit);

    boolean push(FSphere arg);
    boolean push(FSphere arg, List<FSphere> field, int bounce);

    boolean project(FPoint aim, List<FSphere> field);

    //--------------------------------------------------

    @Modificator
    FPoint getRefCenter();
    @Modificator
    FSphere setRefCenter(FPoint refCenter);
}
