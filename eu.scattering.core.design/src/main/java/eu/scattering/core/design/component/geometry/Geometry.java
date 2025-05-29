package eu.scattering.core.design.component.geometry;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import org.json.JSONObject;

import java.util.Collection;

public interface Geometry {

    JSONObject toJSON();

    @Fragment
    Collection<FPoint> explode();

    @Fragment
    Geometry replicate();
}
