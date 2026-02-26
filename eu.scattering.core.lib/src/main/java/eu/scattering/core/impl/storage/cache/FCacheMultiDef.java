package eu.scattering.core.impl.storage.cache;

import eu.scattering.core.design.storage.cache.FCache;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class FCacheMultiDef implements FCache {
    private static final String JSON_TYPE = "type";
    private static final String JSON_SIZE = "size";
    protected static final String JSON_MAIN = "cacheMulti";

    private final Map<Thread, FCache> cache = new HashMap<>();

    private FCacheMultiDef() {}

    public static FCache create() {

        return new FCacheMultiDef();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public <T> boolean put(String key, T value) {

        return getFCache().put(key, value);
    }

    @Override
    public <T> boolean put(Class<T> type, T value) {

        return getFCache().put(type, value);
    }

    @Override
    public <T> T get(String key, Class<T> type) {

        return getFCache().get(key, type);
    }

    @Override
    public <T> T get(Class<T> type) {

        return getFCache().get(type);
    }

    @Override
    public <T> T get(String key, Class<T> type, Function<FCache, T> action) {

        return getFCache().get(key, type, action);
    }

    @Override
    public <T> T get(Class<T> type, Function<FCache, T> action) {

        return getFCache().get(type, action);
    }

    @Override
    public <T> Optional<T> getOptional(String key, Class<T> type) {

        return getFCache().getOptional(key, type);
    }

    @Override
    public <T> Optional<T> getOptional(Class<T> type) {

        return getFCache().getOptional(type);
    }

    @Override
    public boolean delete(String key) {

        return getFCache().delete(key);
    }

    @Override
    public boolean delete(Class<?> type) {

        return getFCache().delete(type);
    }

    @Override
    public int size() {

        return getFCache().size();
    }

    @Override
    public int reset() {

        return getFCache().reset();
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_SIZE, this.cache.size());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {

        return getFCache().hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof FCache)) {
            return false;
        }

        return getFCache().equals(object);
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    //--------------------------------------------------

    private FCache getFCache() {
        Thread thread = Thread.currentThread();

        if (!this.cache.containsKey(thread)) {
            this.cache.put(thread, supplyFCache());
        }

        return this.cache.get(thread);
    }

    //--------------------------------------------------

    private FCache supplyFCache() {

        return FCacheMonoDef.create();
    }
}
