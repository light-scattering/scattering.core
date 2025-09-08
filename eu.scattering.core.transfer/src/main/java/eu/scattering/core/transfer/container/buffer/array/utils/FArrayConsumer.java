package eu.scattering.core.transfer.container.buffer.array.utils;

@FunctionalInterface
public interface FArrayConsumer<T> {

    void apply(int index, double d0, double d1, double d2, T meta);
}
