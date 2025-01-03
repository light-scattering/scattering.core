package eu.scattering.core.design.mutables;

import org.json.JSONObject;

public interface Mutable<T> {

    JSONObject toJSON();
}
