package eu.scattering.core.design.component.geometry;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.base.point.FPoint;

import java.util.Collection;

public interface Geometry extends Component {

    boolean isExact(Geometry arg);
    boolean isSimilar(Geometry arg);

    Geometry copyGeometry();

    //--------------------------------------------------

    @Fragment
    Collection<FPoint> toFPoints();
}
