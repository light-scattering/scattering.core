package eu.scattering.core.geometry;

import org.json.JSONObject;

public interface IGeometry<T> {

    boolean isExact(T element);

    boolean isSimilar(T element);

    JSONObject exportToJSON();

    T importFromJSON(JSONObject json);

    T copy();

    T self();
}
