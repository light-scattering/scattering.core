package eu.scattering.core.design.storage.mutable.buffer.array.utils;

@FunctionalInterface
public interface FArrayConsumer<T> {

    void apply(int index, double d0, double d1, double d2, double data, T meta);
}
