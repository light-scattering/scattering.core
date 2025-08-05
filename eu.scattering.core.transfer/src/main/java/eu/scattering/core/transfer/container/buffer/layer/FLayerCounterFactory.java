package eu.scattering.core.transfer.container.buffer.layer;

import eu.scattering.core.transfer.container.buffer.layer.concrete.FLayerCounterDef;
import org.json.JSONObject;

public interface FLayerCounterFactory {

    default FLayerCounter getFLayerCounter() {

        return FLayerCounterDef.create();
    }

    default FLayerCounter getFLayerCounter(JSONObject json) {

        return FLayerCounterDef.create(json);
    }
}
