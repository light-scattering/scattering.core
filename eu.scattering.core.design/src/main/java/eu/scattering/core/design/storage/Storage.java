package eu.scattering.core.design.storage;

import org.json.JSONObject;

public interface Storage<T> {

    JSONObject toJSON();
}
