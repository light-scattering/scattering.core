package eu.scattering.core.design.functionality;

import java.util.List;
import java.util.stream.Stream;

public interface Producer<T> {

    T produce();

    Stream<T> stream();

    List<T> getList();
    List<T> getListRandomized(int quantity);
    List<T> getListFixed(int quantity);
}
