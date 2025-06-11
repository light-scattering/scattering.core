package eu.scattering.core.design.engine.randomize.generator.core;

import java.util.Optional;

public interface FRandCore {

    Optional<Long> getSeed();

    boolean nextBoolean();

    double nextDouble();
    double nextDouble(double origin, double bound);
}
