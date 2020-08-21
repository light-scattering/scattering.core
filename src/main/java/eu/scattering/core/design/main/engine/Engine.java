package eu.scattering.core.design.main.engine;

import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.Main;
import org.json.JSONObject;

public interface Engine<T> extends Development<T>, Main<T> {

    T importFromJSON(JSONObject json);

    boolean isSimilar(T element);
    boolean isExact(T element);

    T copy();
    T self();
}
