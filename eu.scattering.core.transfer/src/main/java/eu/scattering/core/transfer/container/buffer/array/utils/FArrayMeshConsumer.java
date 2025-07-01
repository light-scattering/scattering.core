package eu.scattering.core.transfer.container.buffer.array.utils;

@FunctionalInterface
public interface FArrayMeshConsumer {

    void apply(int index, int d0, int d1, int d2, double value);
}
