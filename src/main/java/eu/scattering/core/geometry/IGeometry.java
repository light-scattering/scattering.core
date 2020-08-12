package eu.scattering.core.geometry;

import eu.scattering.core.debug.IDev;
import org.json.JSONObject;

public interface IGeometry<T> extends IDev<T> {

    boolean isExact(T element);

    boolean isSimilar(T element);

    JSONObject exportToJSON();

    T importFromJSON(JSONObject json);

    T copy();

    T self();
}
