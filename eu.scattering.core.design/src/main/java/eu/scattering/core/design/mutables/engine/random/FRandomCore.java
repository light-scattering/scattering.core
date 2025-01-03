package eu.scattering.core.design.mutables.engine.random;

import java.util.Optional;

public interface FRandomCore {

    Optional<Long> getSeed();

    boolean nextBoolean();

    double nextDouble();
    double nextDouble(double origin, double bound);
}
