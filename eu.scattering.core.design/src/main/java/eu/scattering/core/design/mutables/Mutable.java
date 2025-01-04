package eu.scattering.core.design.mutables;

import org.json.JSONObject;

public interface Mutable<T> {

    T applyStateFrom(JSONObject json);

    boolean isSimilar(T ref);
    boolean isExact(T ref);

    T self();

    T copy();
    T copyZero();

    JSONObject toJSON();
}
