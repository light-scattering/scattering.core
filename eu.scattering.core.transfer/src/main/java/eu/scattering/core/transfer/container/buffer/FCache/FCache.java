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

    private final Map<String, Object> mapString;
    private final Map<Class<?>, Object> mapClass;

    private FCache() {

        this.mapString = new HashMap<>();
        this.mapClass = new HashMap<>();
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
        Object result = mapString.put(key, value);

        return result != null;
    }

    public <T> boolean put(Class<T> type, T value) {
        Object result = mapClass.put(type, value);

        return result != null;
    }

    public <T> T get(String key, Class<T> type) {

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

        if (!mapString.containsKey(key)) {
            return false;
        }

        mapString.remove(key);

        return true;
    }

    public boolean delete(Class<?> type) {

        if (!mapClass.containsKey(type)) {
            return false;
        }

        mapClass.remove(type);

        return true;
    }

    public int getSize() {

        return mapString.size() + mapClass.size();
    }

    public int reset() {
        int size = getSize();

        mapString.clear();
        mapClass.clear();

        return size;
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
        int hashCode = mapString.hashCode();

        hashCode = 31 * hashCode + mapClass.hashCode();

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
}
