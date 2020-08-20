package eu.scattering.core.design.main.engine;

import eu.scattering.core.design.development.Development;
import org.json.JSONObject;

public interface Engine<T> extends Development<T> {

    boolean isExact(T element);

    boolean isSimilar(T element);

    JSONObject exportToJSON();

    T importFromJSON(JSONObject json);

    T copy();

    T self();
}
