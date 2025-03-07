package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.Collection;
import java.util.List;

public interface FSphere extends Shape<FSphere> {

    double getRadius();
    FSphere setRadius(double radius);

    // boolean setMinRadius(List<FSphere> field, double limit);
//    boolean setMaxRadius(List<FSphere> field, double limit);

    boolean push(FSphere arg, double epsilon);
    int push(FSphere arg, double epsilon, Collection<FSphere> field, int maxBounce);

    boolean project(FPoint aim, List<FSphere> field);

    //--------------------------------------------------

    @Modificator
    FPoint getRefCenter();
    @Modificator
    FSphere setRefCenter(FPoint refCenter);
}
