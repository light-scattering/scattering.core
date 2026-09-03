package eu.scattering.core.impl.aspect.randomize.core;

import eu.scattering.core.design.aspect.randomize.engine.core.FRandEngineCore;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class FRandEngineCoreOptimizedDef implements FRandEngineCore {
    private final ThreadLocalRandom random;

    private FRandEngineCoreOptimizedDef() {

        this.random = ThreadLocalRandom.current();
    }

    public static FRandEngineCore create() {

        return new FRandEngineCoreOptimizedDef();
    }

    @Override
    public Optional<Long> getSeed() {

        return Optional.empty();
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
            return this.random.nextDouble(origin, bound);
        }

        return this.random.nextDouble(bound, origin);
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
    public double nextGaussian() {

        return this.random.nextGaussian();
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
