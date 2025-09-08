package eu.scattering.core.transfer.container.buffer.array.utils;

@FunctionalInterface
public interface FArrayMeshConsumer<T> {

    void apply(int index, int d0, int d1, int d2, T meta);
}
