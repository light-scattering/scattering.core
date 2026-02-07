package eu.scattering.core.design.storage.buffer.util;

@FunctionalInterface
public interface FBufferConsumer<T> {

    void apply(int index, double d0, double d1, double d2, double data, T meta);
}
