package eu.scattering.core.design.storage.layer;

import org.json.JSONObject;

public interface FLayerCounterFactory {

    FLayerCounter getFLayerCounter();

    FLayerCounter getFLayerCounter(JSONObject json);
}
