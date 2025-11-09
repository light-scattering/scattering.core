package eu.scattering.core.design.storage.layer;

import org.json.JSONObject;

public interface FLayerFactory {

    FLayer getFLayer();

    FLayer getFLayer(JSONObject json);
}
