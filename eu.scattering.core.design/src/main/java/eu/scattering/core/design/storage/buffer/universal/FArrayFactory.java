package eu.scattering.core.design.storage.buffer.universal;

public interface FArrayFactory {

    <T> FArray<T> getFArray();

    <T> FArray<T> getFArray(int length);
}
