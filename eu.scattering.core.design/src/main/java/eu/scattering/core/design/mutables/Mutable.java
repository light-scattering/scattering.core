package eu.scattering.core.design.mutables;

import org.json.JSONObject;

public interface Mutable<T> {

    T applyStateFrom(JSONObject json);

    boolean isSimilar(T arg);
    boolean isExact(T arg);

    T self();

    T copy();
    T copyZero();

    JSONObject toJSON();
}
