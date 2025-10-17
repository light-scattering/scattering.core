package eu.scattering.core.design.storage.cache.concurrent;

import org.json.JSONObject;

public interface FCacheThreadFactory {

    FCacheThread getFCacheThread();

    FCacheThread getFCacheThread(JSONObject json);
}
