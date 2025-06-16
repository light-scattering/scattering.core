package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.ShapeCommon;
import eu.scattering.core.design.util.annotation.Modificator;

public interface FSphere extends ShapeCommon<FSphere> {

    @Modificator
    FPoint getRefCenter();
    @Modificator
    FSphere setRefCenter(FPoint refCenter);
}
