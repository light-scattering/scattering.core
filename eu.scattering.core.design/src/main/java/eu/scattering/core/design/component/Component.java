package eu.scattering.core.design.component;

import eu.scattering.core.design.annotation.Fragment;
import org.json.JSONObject;

public interface Component<T> {

    boolean isExact(T arg);

    T copy();

    T set(JSONObject json);

    JSONObject toJSON();

    //--------------------------------------------------

    @Fragment
    T self();
}
