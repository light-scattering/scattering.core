package eu.scattering.core.design.storage.mutable.buffer.array;

public interface FArrayFactory {

    <T> FArray<T> getFArray();

    <T> FArray<T> getFArray(int length);
}
