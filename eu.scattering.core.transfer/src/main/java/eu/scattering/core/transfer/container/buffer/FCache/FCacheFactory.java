package eu.scattering.core.transfer.container.buffer.FCache;

import org.json.JSONObject;

public interface FCacheFactory {

    default FCache getFCache() {

        return FCache.create();
    }

    default FCache getFCache(JSONObject json) {

        return FCache.create(json);
    }
}
