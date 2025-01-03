package eu.scattering.core.impl.production.mutables.engine.random;

import eu.scattering.core.design.mutables.engine.random.FRandomCore;

import java.util.Optional;
import java.util.Random;

public class FRandomCoreSimple implements FRandomCore {
    private final long seed;
    private final Random random;

    private FRandomCoreSimple(long seed) {

        this.seed = seed;
        this.random = new Random(this.seed);
    }

    protected static FRandomCore create(long seed) {

        return new FRandomCoreSimple(seed);
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
