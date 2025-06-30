package eu.scattering.core.transfer.container.buffer.FLayer;

import org.json.JSONObject;

public interface FLayerFactory {

    default FLayerDef getFLayer() {

        return FLayerDef.create();
    }

    default FLayerDef getFLayer(JSONObject json) {

        return FLayerDef.create(json);
    }
}
