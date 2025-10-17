package eu.scattering.core.design.storage.mesh;

public interface FMeshFactory {

    <T> FMesh<T> getFMesh();

    <T> FMesh<T> getFMesh(int capacity);
}
