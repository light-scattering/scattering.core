package eu.scattering.core.transfer.container.buffer.FLayer;

import org.json.JSONObject;

public interface FLayerFactory {

    default FLayer getFLayer() {

        return FLayer.create();
    }

    default FLayer getFLayer(JSONObject json) {

        return FLayer.create(json);
    }
}
