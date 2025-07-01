package eu.scattering.core.transfer.container.buffer.cache;

import eu.scattering.core.transfer.container.buffer.cache.concrete.FCacheThreadDef;
import org.json.JSONObject;

public interface FCacheThreadFactory {

    default FCacheThread getFCacheThread() {

        return FCacheThreadDef.create();
    }

    default FCacheThread getFCacheThread(JSONObject json) {

        return FCacheThreadDef.create(json);
    }
}
