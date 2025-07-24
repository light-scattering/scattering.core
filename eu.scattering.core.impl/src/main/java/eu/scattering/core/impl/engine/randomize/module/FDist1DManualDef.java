package eu.scattering.core.impl.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.custom.FDist1DCustom;

import java.util.function.BiConsumer;

public class FDist1DManualDef implements FDist1DCustom {
    private final BiConsumer<FRandGenerator, Double[]> consumer;
    private final FRandGenerator random;
    private final Double[] arr = new Double[1];

    private FDist1DManualDef(FRandGenerator random, BiConsumer<FRandGenerator, Double[]> consumer) {

        this.random = random;
        this.consumer = consumer;
    }

    public static FDist1DCustom get(FRandGenerator random, BiConsumer<FRandGenerator, Double[]> consumer) {

        return new FDist1DManualDef(random, consumer);
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
