package eu.scattering.core.design.storage.cache;

import eu.scattering.core.design.storage.Storage;

import java.util.Optional;
import java.util.function.Function;

public interface FCache extends Storage<FCache> {

    <T> boolean put(String key, T value);
    <T> boolean put(Class<T> type, T value);

    <T> T get(String key, Class<T> type);
    <T> T get(Class<T> type);

    <T> T get(String key, Class<T> type, Function<FCache, T> action);
    <T> T get(Class<T> type, Function<FCache, T> action);

    <T> Optional<T> getOptional(String key, Class<T> type);
    <T> Optional<T> getOptional(Class<T> type);

    boolean delete(String key);
    boolean delete(Class<?> type);

    int size();

    int reset();
}
