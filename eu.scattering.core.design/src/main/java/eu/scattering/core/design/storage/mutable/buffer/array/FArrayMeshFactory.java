package eu.scattering.core.design.storage.mutable.buffer.array;

public interface FArrayMeshFactory {

    <T> FArrayMesh<T> getFArrayMesh();

    <T> FArrayMesh<T> getFArrayMesh(int capacity);
}
