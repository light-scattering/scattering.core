package eu.scattering.core.design.storage.cache;

import org.json.JSONObject;

public interface FCacheFactory {

    FCache getFCache(boolean multi);

    FCache getFCache(JSONObject json);

    //--------------------------------------------------

    default FCache getFCache() {

        return getFCache(false);
    }
}
