package eu.scattering.core.design.storage.cache.serial;

import org.json.JSONObject;

public interface FCacheFactory {

    FCache getFCache();

    FCache getFCache(JSONObject json);
}
