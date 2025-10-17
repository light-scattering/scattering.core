package eu.scattering.core.design.storage.buffer.mesh;

public interface FArrayMeshFactory {

    <T> FArrayMesh<T> getFArrayMesh();

    <T> FArrayMesh<T> getFArrayMesh(int capacity);
}
