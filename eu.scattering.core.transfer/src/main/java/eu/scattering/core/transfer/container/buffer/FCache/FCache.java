package eu.scattering.core.transfer.container.buffer.FCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class FCache {
    private static final String JSON_MAIN = "cache";

    private final Map<Long, Map<String, Object>> cacheString;
    private final Map<Long, Map<Class<?>, Object>> cacheClass;

    private FCache() {

        this.cacheString = new HashMap<>();
        this.cacheClass = new HashMap<>();
    }

    public static FCache create() {

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

    public <T> Optional<T> get(String key, Class<T> type) {
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

    public <T> Optional<T> get(Class<T> type) {
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

    // -------------------------------------------------------------------------------------------------

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
