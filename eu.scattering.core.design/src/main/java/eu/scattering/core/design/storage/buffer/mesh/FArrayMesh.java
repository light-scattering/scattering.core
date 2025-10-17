package eu.scattering.core.design.storage.buffer.mesh;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.buffer.mesh.utils.FArrayMeshConsumer;
import eu.scattering.core.design.transfer.primitive.FPos3DI;

import java.util.function.BiFunction;

public interface FArrayMesh<T> extends Storage<FArrayMesh<T>> {

    double getData();
    void setData(double data);

    void add(int d0, int d1, int d2);
    void add(FPos3DI pos);

    void addWithMeta(int d0, int d1, int d2, T meta);
    void addWithMeta(FPos3DI pos, T meta);

    T getMeta(int index);

    int getD0(int index);
    int getD1(int index);
    int getD2(int index);

    FPos3DI getFPos3DI(int index);

    int findIndex(int d0, int d1, int d2);
    int findIndex(FPos3DI pos);

    void forEach(FArrayMeshConsumer<T> consumer);

    int size();
    int capacity();

    int deduplicate();
    int deduplicate(BiFunction<T, T, Boolean> collision);

    void clear();
}
