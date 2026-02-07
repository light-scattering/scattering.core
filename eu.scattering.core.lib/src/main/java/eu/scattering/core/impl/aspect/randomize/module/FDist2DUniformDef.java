package eu.scattering.core.impl.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.uniform.FDist2DUniform;
import eu.scattering.core.design.storage.StorageFactory;
import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos2D;
import eu.scattering.core.design.storage.transfer.single.variants.FPos2D;

public class FDist2DUniformDef implements FDist2DUniform {
    private final StorageFactory factory;

    private final FRandGenerator random;
    private final double x1, x2, y1, y2;

    private FDist2DUniformDef(StorageFactory factory, FRandGenerator random, double x1, double x2, double y1, double y2) {

        this.factory = factory;

        this.random = random;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public static FDist2DUniform get(StorageFactory factory, FRandGenerator random, double x1, double x2, double y1, double y2) {

        return new FDist2DUniformDef(factory, random, x1, x2, y1, y2);
    }

    public static FDist2DUniform get(StorageFactory factory, FRandGenerator random, FPairPos2D range) {

        return new FDist2DUniformDef(factory, random,
                range.getPosA().getD0(), range.getPosB().getD0(),
                range.getPosA().getD1(), range.getPosB().getD1()
        );
    }

    @Override
    public FPos2D produce() {

        return factory.getFPos2D(
                this.random.nextDouble(x1, x2),
                this.random.nextDouble(y1, y2)
        );
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.random.nextDouble(x1, x2);
        in[1] = this.random.nextDouble(y1, y2);
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
