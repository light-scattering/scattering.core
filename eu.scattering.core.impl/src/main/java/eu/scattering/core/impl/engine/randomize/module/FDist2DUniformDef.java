package eu.scattering.core.impl.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist2d.uniform.FDist2DUniform;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;

public class FDist2DUniformDef implements FDist2DUniform {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private final FRandGenerator random;
    private final double x1, x2, y1, y2;

    private FDist2DUniformDef(FRandGenerator random, double x1, double x2, double y1, double y2) {

        this.random = random;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public static FDist2DUniform get(FRandGenerator random, double x1, double x2, double y1, double y2) {

        return new FDist2DUniformDef(random, x1, x2, y1, y2);
    }

    public static FDist2DUniform get(FRandGenerator random, FPairPos2D range) {

        return new FDist2DUniformDef(random,
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
