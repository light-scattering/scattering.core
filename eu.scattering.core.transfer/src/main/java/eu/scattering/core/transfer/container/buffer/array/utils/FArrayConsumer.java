package eu.scattering.core.transfer.container.buffer.array.utils;

@FunctionalInterface
public interface FArrayConsumer {

    void apply(int index, double d0, double d1, double d2, double value);
}
