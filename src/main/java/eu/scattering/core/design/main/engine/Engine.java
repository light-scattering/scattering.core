package eu.scattering.core.design.main.engine;

import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.Main;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import org.json.JSONObject;

import java.util.List;

public interface Engine<T> extends Development<T>, Main<T> {

    T importFromJSON(JSONObject json);

    boolean isSimilar(T element);
    boolean isExact(T element);

    T copy();
    T self();
}
