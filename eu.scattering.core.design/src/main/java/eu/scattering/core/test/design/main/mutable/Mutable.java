package eu.scattering.core.test.design.main.mutable;

import eu.scattering.core.test.design.development.Development;
import eu.scattering.core.test.design.main.Main;
import org.json.JSONObject;

public interface Mutable<T> extends Development<T>, Main<T> {

    T importFromJSON(JSONObject json);

    boolean isSimilar(T element);
    boolean isExact(T element);

    T copy();
    T self();
}
