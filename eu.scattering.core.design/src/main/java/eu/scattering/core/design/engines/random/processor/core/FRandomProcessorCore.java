package eu.scattering.core.design.engines.random.processor;

import java.util.Optional;

public interface FRandomProcessorCore {

    Optional<Long> getSeed();

    boolean nextBoolean();

    double nextDouble();
    double nextDouble(double origin, double bound);
}
