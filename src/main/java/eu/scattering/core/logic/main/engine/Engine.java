package eu.scattering.core.logic.main.engine;

import eu.scattering.core.logic.dev.Dev;
import org.json.JSONObject;

public interface Engine<T> extends Dev<T> {

    boolean isExact(T element);

    boolean isSimilar(T element);

    JSONObject exportToJSON();

    T importFromJSON(JSONObject json);

    T copy();

    T self();
}
