package eu.scattering.core.design.util.support;

import java.util.stream.Stream;

public interface Producer<T> {

    T produce();

    Stream<T> stream();
}
