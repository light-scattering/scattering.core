package eu.scattering.core.design.component;

import org.json.JSONObject;

public interface Component<T> {

    T applyStateFrom(JSONObject json);

    boolean isSimilar(T arg);
    boolean isExact(T arg);

    T self();

    T copy();
    T copyZero();

    JSONObject toJSON();
}
