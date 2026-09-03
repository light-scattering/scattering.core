package eu.scattering.core.impl.aspect.randomize.distribution.dist2D;

import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.uniform.FRandDist2DUniform;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public class FRandDist2DUniformDef implements FRandDist2DUniform {
    private final TransferFactory factoryExt;

    private final FRandEngine random;
    private final double d0min, d0max, d1min, d1max;

    private FRandDist2DUniformDef(TransferFactory factoryExt, FRandEngine random, double d0min, double d0max, double d1min, double d1max) {
        this.factoryExt = factoryExt;

        this.random = random;
        this.d0min = d0min;
        this.d0max = d0max;
        this.d1min = d1min;
        this.d1max = d1max;
    }

    public static FRandDist2DUniform create(TransferFactory factoryExt, FRandEngine random, double d0min, double d0max, double d1min, double d1max) {

        return new FRandDist2DUniformDef(factoryExt, random, d0min, d0max, d1min, d1max);
    }

    public static FRandDist2DUniform create(TransferFactory factoryExt, FRandEngine random, FPairPos2D range) {

        return new FRandDist2DUniformDef(factoryExt, random,
                range.getPosA().getD0(), range.getPosB().getD0(),
                range.getPosA().getD1(), range.getPosB().getD1()
        );
    }

    @Override
    public FPos2D produce() {

        return factoryExt.getFPos2D(
                this.random.nextDouble(d0min, d0max),
                this.random.nextDouble(d1min, d1max)
        );
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.random.nextDouble(d0min, d0max);
        in[1] = this.random.nextDouble(d1min, d1max);
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
