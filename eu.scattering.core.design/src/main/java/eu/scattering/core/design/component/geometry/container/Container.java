package eu.scattering.core.design.component.geometry.container;

import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.design.component.geometry.Geometry;
import org.json.JSONObject;

public interface Container<T> extends Geometry {

    T set(JSONObject json);

    boolean isExact(T arg);
    boolean isSimilar(T arg);

    T copy();

    //--------------------------------------------------

    @Fragment
    T self();
}
