package eu.scattering.core.transfer.container.buffer.array;

import eu.scattering.core.transfer.container.buffer.Buffer;
import eu.scattering.core.transfer.container.buffer.array.utils.FArrayMeshConsumer;
import eu.scattering.core.transfer.container.storage.FPos3DI.FPos3DI;
import eu.scattering.core.transfer.container.storage.FPos4DI.FPos4DI;

public interface FArrayMesh extends Buffer<FArrayMesh>, Iterable<double[]> {

    void add(int d0, int d1, int d2);
    void add(FPos3DI pos);

    void addWithValue(int d0, int d1, int d2, double value);
    void addWithValue(FPos3DI pos, double value);
    void addWithValue(FPos4DI pos);

    FPos3DI getFPos3DI(int index);
    FPos4DI getFPos4DI(int index);

    int getD0(int index);
    int getD1(int index);
    int getD2(int index);

    double getValue(int index);

    void forEach(FArrayMeshConsumer consumer);

    int size();
    int capacity();

    void reset();
}
