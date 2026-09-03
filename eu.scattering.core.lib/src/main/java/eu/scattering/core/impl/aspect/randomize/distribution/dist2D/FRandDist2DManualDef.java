package eu.scattering.core.impl.aspect.randomize.distribution.dist2D;

import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.custom.FRandDist2DCustom;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

import java.util.function.BiConsumer;

public class FRandDist2DManualDef implements FRandDist2DCustom {
    private final TransferFactory factoryExt;

    private final BiConsumer<FRandEngine, Double[]> consumer;
    private final FRandEngine random;
    private final Double[] arr = new Double[2];

    private FRandDist2DManualDef(TransferFactory factoryExt, FRandEngine random, BiConsumer<FRandEngine, Double[]> consumer) {
        this.factoryExt = factoryExt;

        this.random = random;
        this.consumer = consumer;
    }

    public static FRandDist2DCustom create(TransferFactory factoryExt, FRandEngine random, BiConsumer<FRandEngine, Double[]> consumer) {

        return new FRandDist2DManualDef(factoryExt, random, consumer);
    }

    @Override
    public FPos2D produce() {

        this.consumer.accept(this.random, this.arr);

        return factoryExt.getFPos2D(this.arr[0], this.arr[1]);
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        this.consumer.accept(this.random, this.arr);

        in[0] = this.arr[0];
        in[1] = this.arr[1];
    }

    private void validate(double[] in) {

        if (in == null) {
            throw new NullPointerException("The input array is null");
        }

        if (in.length < 2) {
            throw new IllegalArgumentException("The input array does not contain the required number of elements");
        }
    }
}
