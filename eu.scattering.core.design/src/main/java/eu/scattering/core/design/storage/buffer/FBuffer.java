package eu.scattering.core.design.storage.buffer;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.storage.buffer.util.FBufferConsumer;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

import java.util.function.BiFunction;

public interface FBuffer<T> extends Storage {

    void add(double d0, double d1, double d2);
    void add(FPos3D pos);

    void addWithData(double d0, double d1, double d2, double data);
    void addWithData(FPos3D pos, double data);

    void addWithMeta(double d0, double d1, double d2, T meta);
    void addWithMeta(FPos3D pos, T meta);

    void addWithDataAndMeta(double d0, double d1, double d2, double data, T meta);
    void addWithDataAndMeta(FPos3D pos, double data, T meta);

    FPos3D getFPos3D(int index);

    double getD0(int index);
    double getD1(int index);
    double getD2(int index);

    double getData(int index);

    T getMeta(int index);

    int findIndex(double d0, double d1, double d2);
    int findIndex(FPos3D pos);

    void forEach(FBufferConsumer<T> consumer);

    int size();
    int capacity();

    int deduplicate();
    int deduplicate(BiFunction<T, T, Boolean> collision);

    FMesh<T> toFArrayMesh();

    void clear();
}
