package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.util.annotation.Fragment;
import org.json.JSONObject;

public interface ShapeCommon<T> extends Shape {

    T set(JSONObject json);

    T applyStateTo(T in);
    T applyStateFrom(T arg);

    T copy();

    //--------------------------------------------------

    @Fragment
    T self();
}
