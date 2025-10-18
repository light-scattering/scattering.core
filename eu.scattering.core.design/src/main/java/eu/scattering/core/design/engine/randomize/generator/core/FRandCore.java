package eu.scattering.core.design.engine.randomize.generator.core;

import java.util.List;
import java.util.Optional;

public interface FRandCore {

    Optional<Long> getSeed();

    boolean nextBoolean();

    double nextDouble();
    double nextDouble(double origin, double bound);

    long nextLong();
    long nextLong(long origin, long bound);

    int nextInteger();
    int nextInteger(int origin, int bound);

    // -------------------------------------------------------------------------------------------------

    double nextGaussian(double mean, double std);

    // -------------------------------------------------------------------------------------------------

    <T> void shuffle(List<T> in);
}
