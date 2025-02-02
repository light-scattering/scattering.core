package eu.scattering.core.design.engines.rand.processor.core;

import java.util.Optional;

public interface FRandProcessorCore {

    Optional<Long> getSeed();

    boolean nextBoolean();

    double nextDouble();
    double nextDouble(double origin, double bound);
}
