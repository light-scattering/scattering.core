package eu.scattering.core.design.core.mutable;

import eu.scattering.core.design.debug.Debug;
import eu.scattering.core.design.core.Core;
import org.json.JSONObject;

public interface Mutable<T> extends Debug<T>, Core<T> {
// TODO Move everything to 'Core.java'
    T importFromJSON(JSONObject json);

    boolean isSimilar(T element);
    boolean isExact(T element);

    T copy();
    T self();
}
