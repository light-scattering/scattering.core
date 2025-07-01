package eu.scattering.core.transfer.container.buffer.cache;

import eu.scattering.core.transfer.container.buffer.cache.concrete.FCacheDef;
import org.json.JSONObject;

public interface FCacheFactory {

    default FCache getFCache() {

        return FCacheDef.create();
    }

    default FCache getFCache(JSONObject json) {

        return FCacheDef.create(json);
    }
}
