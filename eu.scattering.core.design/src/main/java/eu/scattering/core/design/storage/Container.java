package eu.scattering.core.design.storage;

import org.json.JSONObject;

public interface Container<T> {

    JSONObject toJSON();
}
