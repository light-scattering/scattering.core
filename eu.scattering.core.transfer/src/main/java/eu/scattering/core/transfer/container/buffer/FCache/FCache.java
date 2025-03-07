package eu.scattering.core.transfer.container.buffer.FCache;

import eu.scattering.core.transfer.container.buffer.Buffer;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FCache implements Buffer<FCache> {
    private static final String JSON_MAIN = "cache";
    private static final String JSON_SIZE = "size";

    private final Map<Long, Map<String, Object>> cacheString;
    private final Map<Long, Map<Class<?>, Object>> cacheClass;

    private FCache() {

        this.cacheString = new HashMap<>();
        this.cacheClass = new HashMap<>();
    }

    protected static FCache create() {

        return new FCache();
    }

    protected static FCache create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        return new FCache();
    }

    // -------------------------------------------------------------------------------------------------

    public <T> boolean put(String key, T value) {
        long id = Thread.currentThread().getId();

        Map<String, Object> mapString = getMapString(id);

        Object result = mapString.put(key, value);

        return result != null;
    }

    public <T> boolean put(Class<T> type, T value) {
        long id = Thread.currentThread().getId();

        Map<Class<?>, Object> mapClass = getMapClass(id);

        Object result = mapClass.put(type, value);

        return result != null;
    }

    public <T> T get(String key, Class<T> type) {
        long id = Thread.currentThread().getId();

        Map<String, Object> mapString = getMapString(id);

        if (!mapString.containsKey(key)) {
            throw new IllegalArgumentException("The object does not exist");
        }

        try {
            return type.cast(mapString.get(key));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The object type is erroneous");
        }
    }

    public <T> T get(Class<T> type) {
        long id = Thread.currentThread().getId();

        Map<Class<?>, Object> mapClass = getMapClass(id);

        if (!mapClass.containsKey(type)) {
            throw new IllegalArgumentException("The object does not exist");
        }

        try {
            return type.cast(mapClass.get(type));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The object type is erroneous");
        }
    }

    public <T> T get(String key, Class<T> type, Function<FCache, T> action) {
        long id = Thread.currentThread().getId();

        Map<String, Object> mapString = getMapString(id);

        if (!mapString.containsKey(key)) {
            mapString.put(key, action.apply(this));
        }
        try {
            return type.cast(mapString.get(key));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The object type is erroneous");
        }

    }

    public <T> T get(Class<T> type, Function<FCache, T> action) {
        long id = Thread.currentThread().getId();

        Map<Class<?>, Object> mapClass = getMapClass(id);

        if (!mapClass.containsKey(type)) {
            mapClass.put(type, action.apply(this));
        }

        try {
            return type.cast(mapClass.get(type));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The object type is erroneous");
        }
    }

    public <T> Optional<T> getOptional(String key, Class<T> type) {
        long id = Thread.currentThread().getId();

        Map<String, Object> mapString = getMapString(id);

        if (!mapString.containsKey(key)) {
            return Optional.empty();
        }

        try {
            return Optional.of(type.cast(mapString.get(key)));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The object type is erroneous");
        }
    }

    public <T> Optional<T> getOptional(Class<T> type) {
        long id = Thread.currentThread().getId();

        Map<Class<?>, Object> mapClass = getMapClass(id);

        if (!mapClass.containsKey(type)) {
            return Optional.empty();
        }

        try {
            return Optional.of(type.cast(mapClass.get(type)));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The object type is erroneous");
        }
    }

    public boolean delete(String key) {
        long id = Thread.currentThread().getId();

        Map<String, Object> mapString = getMapString(id);

        if (!mapString.containsKey(key)) {
            return false;
        }

        mapString.remove(key);

        return true;
    }

    public boolean delete(Class<?> type) {
        long id = Thread.currentThread().getId();

        Map<Class<?>, Object> mapClass = getMapClass(id);

        if (!mapClass.containsKey(type)) {
            return false;
        }

        mapClass.remove(type);

        return true;
    }

    public int getSize() {
        long id = Thread.currentThread().getId();

        Map<String, Object> mapString = getMapString(id);
        Map<Class<?>, Object> mapClass = getMapClass(id);

        return mapString.size() + mapClass.size();
    }

    public int reset() {
        long id = Thread.currentThread().getId();

        Map<String, Object> mapString = getMapString(id);
        Map<Class<?>, Object> mapClass = getMapClass(id);

        int size = getSize();

        mapString.clear();
        mapClass.clear();

        return size;
    }

    // -------------------------------------------------------------------------------------------------

    public int getNumberOfThreads() {

        return Math.max(cacheString.size(), cacheClass.size());
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_SIZE, getSize());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {
        int hashCode = cacheString.hashCode();

        hashCode = 31 * hashCode + cacheClass.hashCode();

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        return this == object;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    //--------------------------------------------------

    private Map<String, Object> getMapString(long id) {

        if (cacheString.containsKey(id)) {
            return cacheString.get(id);
        }

        var map = new HashMap<String, Object>();

        cacheString.put(id, map);

        return map;
    }

    private Map<Class<?>, Object> getMapClass(long id) {

        if (cacheClass.containsKey(id)) {
            return cacheClass.get(id);
        }

        var map = new HashMap<Class<?>, Object>();

        cacheClass.put(id, map);

        return map;
    }
}
