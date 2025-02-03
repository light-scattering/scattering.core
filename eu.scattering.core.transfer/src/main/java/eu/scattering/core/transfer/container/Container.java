package eu.scattering.core.transfer.container;

import org.json.JSONObject;

public interface Container<T> {

    JSONObject toJSON();
}
