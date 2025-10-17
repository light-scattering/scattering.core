package eu.scattering.core.design.storage.mesh.utils;

@FunctionalInterface
public interface FMeshConsumer<T> {

    void apply(int index, int d0, int d1, int d2, T meta);
}
