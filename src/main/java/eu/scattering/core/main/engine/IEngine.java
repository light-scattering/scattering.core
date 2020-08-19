package eu.scattering.core.main.engine;

import eu.scattering.core.dev.IDev;
import org.json.JSONObject;

public interface IEngine<T> extends IDev<T> {

    boolean isExact(T element);

    boolean isSimilar(T element);

    JSONObject exportToJSON();

    T importFromJSON(JSONObject json);

    T copy();

    T self();
}
