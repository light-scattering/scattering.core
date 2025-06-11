package eu.scattering.core.impl.engine.randomize.core;

import eu.scattering.core.design.engine.randomize.generator.core.FRandCore;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class FRandCoreOptimizedDef implements FRandCore {

    private FRandCoreOptimizedDef() {}

    public static FRandCore create() {

        return new FRandCoreOptimizedDef();
    }

    @Override
    public Optional<Long> getSeed() {

        return Optional.empty();
    }

    @Override
    public boolean nextBoolean() {

        return ThreadLocalRandom.current().nextBoolean();
    }

    @Override
    public double nextDouble() {

        return ThreadLocalRandom.current().nextDouble();
    }

    @Override
    public double nextDouble(double origin, double bound) {

        if (origin == bound) {
            throw new IllegalArgumentException("The range cannot be zero");
        }

        if (bound > origin) {
            return ThreadLocalRandom.current().nextDouble(origin, bound);
        }

        return ThreadLocalRandom.current().nextDouble(bound, origin);
    }
}
