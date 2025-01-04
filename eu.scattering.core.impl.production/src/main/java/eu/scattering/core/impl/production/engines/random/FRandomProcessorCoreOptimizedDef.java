package eu.scattering.core.impl.production.engines.random.core;

import eu.scattering.core.design.engines.random.processor.core.FRandomProcessorCore;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class FRandomProcessorCoreOptimizedDef implements FRandomProcessorCore {

    private FRandomProcessorCoreOptimizedDef() {}

    protected static FRandomProcessorCore create() {

        return new FRandomProcessorCoreOptimizedDef();
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
