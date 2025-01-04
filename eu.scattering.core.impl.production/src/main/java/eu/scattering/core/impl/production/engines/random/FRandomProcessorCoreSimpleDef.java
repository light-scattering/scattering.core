package eu.scattering.core.impl.production.engines.random.core;

import eu.scattering.core.design.engines.random.processor.core.FRandomProcessorCore;

import java.util.Optional;
import java.util.Random;

public class FRandomProcessorCoreSimpleDef implements FRandomProcessorCore {
    private final long seed;
    private final Random random;

    private FRandomProcessorCoreSimpleDef(long seed) {

        this.seed = seed;
        this.random = new Random(this.seed);
    }

    protected static FRandomProcessorCore create(long seed) {

        return new FRandomProcessorCoreSimpleDef(seed);
    }

    @Override
    public Optional<Long> getSeed() {

        return Optional.of(this.seed);
    }

    @Override
    public boolean nextBoolean() {

        return random.nextBoolean();
    }

    @Override
    public double nextDouble() {

        return random.nextDouble();
    }

    @Override
    public double nextDouble(double origin, double bound) {

        if (origin == bound) {
            throw new IllegalArgumentException("The range cannot be zero");
        }

        if (bound > origin) {
            return (nextDouble() * (bound - origin)) + origin;
        }

        return (nextDouble() * (origin - bound)) + bound;
    }
}
