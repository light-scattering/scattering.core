package eu.scattering.core.transfer.container.buffer.array;

import eu.scattering.core.transfer.container.buffer.Buffer;
import eu.scattering.core.transfer.container.buffer.array.utils.FArrayConsumer;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;

import java.util.function.BiConsumer;

public interface FArray extends Buffer<FArray>, Iterable<double[]> {

    void add(double d0, double d1, double d2);
    void add(FPos3D pos);

    void addWithValue(double d0, double d1, double d2, double value);
    void addWithValue(FPos3D pos, double value);
    void addWithValue(FPos4D pos);

    FPos3D getFPos3D(int index);
    FPos4D getFPos4D(int index);

    double getD0(int index);
    double getD1(int index);
    double getD2(int index);

    double getValue(int index);

    void forEach(FArrayConsumer consumer);

    int size();
    int capacity();

    void reset();
}
