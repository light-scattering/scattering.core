package eu.scattering.core.transfer.container.buffer.FCache;

import org.json.JSONObject;

public interface FCacheFactory {

    default FCache getFCache() {

        return FCacheDef.create();
    }

    default FCache getFCache(JSONObject json) {

        return FCacheDef.create(json);
    }

    default FCache getFCacheThread() {

        return FCacheThreadDef.create();
    }

    default FCache getFCacheThread(JSONObject json) {

        return FCacheThreadDef.create(json);
    }
}
