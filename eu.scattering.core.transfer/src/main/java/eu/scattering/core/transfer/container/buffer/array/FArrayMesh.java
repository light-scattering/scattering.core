package eu.scattering.core.transfer.container.buffer.array;

import eu.scattering.core.transfer.container.buffer.Buffer;
import eu.scattering.core.transfer.container.buffer.array.utils.FArrayMeshConsumer;

public interface FArrayMesh extends Buffer<FArrayMesh>, Iterable<double[]> {

    void add(int d0, int d1, int d2);
    void add(int d0, int d1, int d2, double value);

    int getD0(int index);
    int getD1(int index);
    int getD2(int index);
    double getValue(int index);

    void iterate(FArrayMeshConsumer consumer);

    int size();
    int capacity();

    void reset();
}
