package eu.scattering.core.impl.engine.randomize;

import eu.scattering.core.design.engine.randomize.processor.core.FRandProcessorCore;

import java.util.Optional;
import java.util.Random;

public class FRandCoreSimpleDef implements FRandProcessorCore {
    private final long seed;
    private final Random random;

    private FRandCoreSimpleDef(long seed) {

        this.seed = seed;
        this.random = new Random(this.seed);
    }

    protected static FRandProcessorCore create(long seed) {

        return new FRandCoreSimpleDef(seed);
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
