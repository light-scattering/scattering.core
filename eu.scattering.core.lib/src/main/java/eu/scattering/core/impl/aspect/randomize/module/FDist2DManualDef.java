package eu.scattering.core.impl.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.custom.FDist2DCustom;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

import java.util.function.BiConsumer;

public class FDist2DManualDef implements FDist2DCustom {
    private final TransferFactory factoryExt;

    private final BiConsumer<FRandGenerator, Double[]> consumer;
    private final FRandGenerator random;
    private final Double[] arr = new Double[2];

    private FDist2DManualDef(TransferFactory factoryExt, FRandGenerator random, BiConsumer<FRandGenerator, Double[]> consumer) {
        this.factoryExt = factoryExt;

        this.random = random;
        this.consumer = consumer;
    }

    public static FDist2DCustom create(TransferFactory factoryExt, FRandGenerator random, BiConsumer<FRandGenerator, Double[]> consumer) {

        return new FDist2DManualDef(factoryExt, random, consumer);
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
