package eu.scattering.core.design.component.geometry;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.component.geometry.base.point.FPoint;

import java.util.Collection;

public interface Geometry {

    @Fragment
    Collection<FPoint> toFPoints();
}
