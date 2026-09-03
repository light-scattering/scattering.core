package eu.scattering.core.impl.aspect.randomize.distribution.dist1D;

import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.custom.FRandDist1DCustom;

import java.util.function.BiConsumer;

public class FRandDist1DManualDef implements FRandDist1DCustom {
    private final BiConsumer<FRandEngine, Double[]> consumer;
    private final FRandEngine random;
    private final Double[] arr = new Double[1];

    private FRandDist1DManualDef(FRandEngine random, BiConsumer<FRandEngine, Double[]> consumer) {

        this.random = random;
        this.consumer = consumer;
    }

    public static FRandDist1DCustom get(FRandEngine random, BiConsumer<FRandEngine, Double[]> consumer) {

        return new FRandDist1DManualDef(random, consumer);
    }

    @Override
    public double produce() {

        this.consumer.accept(this.random, this.arr);

        return this.arr[0];
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        this.consumer.accept(this.random, this.arr);

        in[0] = this.arr[0];
    }

    private void validate(double[] in) {

        if (in == null) {
            throw new NullPointerException("The input array is null");
        }

        if (in.length < 1) {
            throw new IllegalArgumentException("The input array does not contain the required number of elements");
        }
    }
}
