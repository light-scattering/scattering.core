package eu.scattering.core.transfer.container.buffer.array;

import eu.scattering.core.transfer.container.buffer.Buffer;
import eu.scattering.core.transfer.container.buffer.array.utils.FArrayConsumer;

public interface FArray extends Buffer<FArray>, Iterable<double[]> {

    void add(double d0, double d1, double d2);
    void add(double d0, double d1, double d2, double value);

    double getD0(int index);
    double getD1(int index);
    double getD2(int index);
    double getValue(int index);

    void iterate(FArrayConsumer consumer);

    int size();
    int capacity();

    void reset();
}
