package eu.scattering.core.design.elements.algebra;

import eu.scattering.core.design.elements.Core;
import org.json.JSONObject;

public interface Algebra<T> extends Core<T> {

    T importFromJSON(JSONObject json);

    boolean isSimilar(T element);
    boolean isExact(T element);

    T copy();
    T self();
}
