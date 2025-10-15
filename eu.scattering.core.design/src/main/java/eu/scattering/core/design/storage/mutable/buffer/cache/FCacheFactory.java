package eu.scattering.core.design.storage.mutable.buffer.cache;

import org.json.JSONObject;

public interface FCacheFactory {

    FCache getFCache();

    FCache getFCache(JSONObject json);
}
