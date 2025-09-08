package eu.scattering.core.transfer.container.buffer.array;

import eu.scattering.core.transfer.container.buffer.Buffer;
import eu.scattering.core.transfer.container.buffer.array.utils.FArrayConsumer;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.BiFunction;

public interface FArray<T> extends Buffer<FArray<T>> {

    void add(double d0, double d1, double d2);
    void add(FPos3D pos);

    void addWithMeta(double d0, double d1, double d2, T meta);
    void addWithMeta(FPos3D pos, T meta);

    T getMeta(int index);

    double getD0(int index);
    double getD1(int index);
    double getD2(int index);

    FPos3D getFPos3D(int index);

    int findIndex(double d0, double d1, double d2);
    int findIndex(FPos3D pos);

    void forEach(FArrayConsumer<T> consumer);

    int size();
    int capacity();

    int deduplicate();
    int deduplicate(BiFunction<T, T, Boolean> collision);

    void clear();
}
