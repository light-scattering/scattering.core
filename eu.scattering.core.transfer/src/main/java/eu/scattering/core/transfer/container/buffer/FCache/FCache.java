package eu.scattering.core.transfer.container.buffer.FCache;

import java.util.HashMap;
import java.util.Map;

public class FCache {
    private static final String JSON_MAIN = "cache";
    private static final String JSON_VAL = "dump";

    private final Map<Long, Map<String, Object>> cache;

    private FCache() {

        this.cache = new HashMap<>();
    }

    public <T> T get(String key, Class<T> type) {

        long threadId = Thread.currentThread().getId();

        if (!cache.containsKey(threadId)) {
            throw new IllegalArgumentException("The thread is not registered");
        }

        Map<String, Object> data = cache.get(threadId);

        if (!data.containsKey(key)) {
            throw new IllegalArgumentException("The value does not exist");
        }

        return type.cast(data.get(key));
    }

    public <T> T get(String key, Class<T> type, T element) {

        long threadId = Thread.currentThread().getId();

        if (!cache.containsKey(threadId)) {
            cache.put(threadId, new HashMap<>());
        }

        Map<String, Object> data = cache.get(threadId);

        if (!data.containsKey(key)) {
            data.put(key, element);
        }

        return type.cast(data.get(key));
    }

    public void set(String key, Object element) {

        long threadId = Thread.currentThread().getId();

        if (!cache.containsKey(threadId)) {
            cache.put(threadId, new HashMap<>());
        }

        Map<String, Object> data = cache.get(threadId);

        data.put(key, element);
    }
}
