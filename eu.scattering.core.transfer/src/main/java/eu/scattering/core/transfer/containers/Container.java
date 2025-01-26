package eu.scattering.core.transfer.containers;

import org.json.JSONObject;

public interface Container<T> {

    JSONObject toJSON();
}
