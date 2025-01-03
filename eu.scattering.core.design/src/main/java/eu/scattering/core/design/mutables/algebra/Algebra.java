package eu.scattering.core.design.mutables.algebra;

import eu.scattering.core.design.mutables.Mutable;
import org.json.JSONObject;

public interface Algebra<T> extends Mutable<T> {

    T applyStateFrom(JSONObject json);

    boolean isSimilar(T ref);
    boolean isExact(T ref);

    T self();

    T copy();
    T copyZero();
}
