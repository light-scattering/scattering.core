package eu.scattering.core.impl.aspect.randomize.distribution.dist3D;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.custom.FDist3DCustom;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

import java.util.function.BiConsumer;

public class FDist3DManualDef implements FDist3DCustom {
    private final TransferFactory factoryExt;

    private final BiConsumer<FRandGenerator, Double[]> consumer;
    private final FRandGenerator random;
    private final Double[] arr = new Double[3];

    private FDist3DManualDef(TransferFactory factoryExt, FRandGenerator random, BiConsumer<FRandGenerator, Double[]> consumer) {
        this.factoryExt = factoryExt;

        this.random = random;
        this.consumer = consumer;
    }

    public static FDist3DCustom create(TransferFactory factoryExt, FRandGenerator random, BiConsumer<FRandGenerator, Double[]> consumer) {

        return new FDist3DManualDef(factoryExt, random, consumer);
    }

    @Override
    public FPos3D produce() {

        this.consumer.accept(this.random, this.arr);

        return factoryExt.getFPos3D(this.arr[0], this.arr[1], this.arr[2]);
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        this.consumer.accept(this.random, this.arr);

        in[0] = this.arr[0];
        in[1] = this.arr[1];
        in[2] = this.arr[2];
    }

    private void validate(double[] in) {

        if (in == null) {
            throw new NullPointerException("The input array is null");
        }

        if (in.length < 3) {
            throw new IllegalArgumentException("The input array does not contain the required number of elements");
        }
    }
}
