package eu.scattering.core.impl.engine.randomize.core;

import eu.scattering.core.design.engine.randomize.generator.core.FRandCore;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class FRandCoreSimpleDef implements FRandCore {
    private final long seed;
    private final Random random;

    private FRandCoreSimpleDef(long seed) {

        this.seed = seed;
        this.random = new Random(this.seed);
    }

    public static FRandCore create(long seed) {

        return new FRandCoreSimpleDef(seed);
    }

    @Override
    public Optional<Long> getSeed() {

        return Optional.of(this.seed);
    }

    @Override
    public boolean nextBoolean() {

        return this.random.nextBoolean();
    }

    @Override
    public double nextDouble() {

        return this.random.nextDouble();
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

    @Override
    public long nextLong() {

        return this.random.nextLong();
    }

    @Override
    public long nextLong(long origin, long bound) {

        if (origin == bound) {
            throw new IllegalArgumentException("The range cannot be zero");
        }

        if (bound > origin) {
            return this.random.nextLong(origin, bound);
        }

        return this.random.nextLong(bound, origin);
    }

    @Override
    public int nextInteger() {

        return this.random.nextInt();
    }

    @Override
    public int nextInteger(int origin, int bound) {

        if (origin == bound) {
            throw new IllegalArgumentException("The range cannot be zero");
        }

        if (bound > origin) {
            return this.random.nextInt(origin, bound);
        }

        return this.random.nextInt(bound, origin);
    }

    @Override
    public double nextGaussian(double mean, double std) {

        return this.random.nextGaussian(mean, std);
    }

    @Override
    public <T> void shuffle(List<T> in) {

        Collections.shuffle(in, this.random);
    }
}
