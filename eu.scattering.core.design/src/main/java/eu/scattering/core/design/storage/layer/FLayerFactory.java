package eu.scattering.core.design.storage.layer;

import org.json.JSONObject;

public interface FLayerFactory {

    FLayer getFLayerCounter();

    FLayer getFLayerCounter(JSONObject json);
}
