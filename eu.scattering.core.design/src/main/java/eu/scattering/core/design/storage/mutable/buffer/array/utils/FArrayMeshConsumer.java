package eu.scattering.core.design.storage.mutable.buffer.array.utils;

@FunctionalInterface
public interface FArrayMeshConsumer<T> {

    void apply(int index, int d0, int d1, int d2, T meta);
}
