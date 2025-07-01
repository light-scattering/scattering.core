package eu.scattering.core.transfer.container.buffer.layer;

import eu.scattering.core.transfer.container.buffer.layer.concrete.FLayerDef;
import org.json.JSONObject;

public interface FLayerFactory {

    default FLayer getFLayer() {

        return FLayerDef.create();
    }

    default FLayer getFLayer(JSONObject json) {

        return FLayerDef.create(json);
    }
}
