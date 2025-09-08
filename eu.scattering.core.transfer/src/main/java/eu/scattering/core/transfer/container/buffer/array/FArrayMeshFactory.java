package eu.scattering.core.transfer.container.buffer.array;

import eu.scattering.core.transfer.container.buffer.array.concrete.FArrayMeshDef;

public interface FArrayMeshFactory {

    default <T> FArrayMesh<T> getFArrayMesh(int capacity) {

        return FArrayMeshDef.create(capacity);
    }
}
