package eu.scattering.core.impl.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.custom.FDist3DCustom;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.BiConsumer;

public class FDist3DManualDef implements FDist3DCustom {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private final BiConsumer<FRandGenerator, Double[]> consumer;
    private final FRandGenerator random;
    private final Double[] arr = new Double[3];

    private FDist3DManualDef(FRandGenerator random, BiConsumer<FRandGenerator, Double[]> consumer) {

        this.random = random;
        this.consumer = consumer;
    }

    public static FDist3DCustom get(FRandGenerator random, BiConsumer<FRandGenerator, Double[]> consumer) {

        return new FDist3DManualDef(random, consumer);
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
