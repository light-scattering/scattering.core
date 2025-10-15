package eu.scattering.core.design.storage.mutable.buffer.cache;

import org.json.JSONObject;

public interface FCacheThreadFactory {

    FCacheThread getFCacheThread();

    FCacheThread getFCacheThread(JSONObject json);
}
