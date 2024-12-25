package eu.scattering.core.design.elements.algebra;

import eu.scattering.core.design.elements.Core;
import org.json.JSONObject;

public interface Algebra<T> extends Core<T> {

    T importFromJSON(JSONObject json);

    boolean isSimilar(T ref);
    boolean isExact(T ref);

    T copy();
    T self();
}
