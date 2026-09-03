package eu.scattering.core.impl.aspect.randomize.distribution.dist3D;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.uniform.FDist3DUniform;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public class FDist3DUniformDef implements FDist3DUniform {
    private final TransferFactory factoryExt;

    private final FRandGenerator random;
    private final double d0min, d0max, d1min, d1max, d2min, d2max;

    private FDist3DUniformDef(TransferFactory factoryExt, FRandGenerator random, double d0min, double d0max, double d1min, double d1max, double d2min, double d2max) {
        this.factoryExt = factoryExt;

        this.random = random;
        this.d0min = d0min;
        this.d0max = d0max;
        this.d1min = d1min;
        this.d1max = d1max;
        this.d2min = d2min;
        this.d2max = d2max;
    }

    public static FDist3DUniform create(TransferFactory factoryExt, FRandGenerator random, double d0min, double d0max, double d1min, double d1max, double d2min, double d2max) {

        return new FDist3DUniformDef(factoryExt, random, d0min, d0max, d1min, d1max, d2min, d2max);
    }

    public static FDist3DUniform create(TransferFactory factoryExt, FRandGenerator random, FPairPos3D range) {

        return new FDist3DUniformDef(factoryExt, random,
                range.getPosA().getD0(), range.getPosB().getD0(),
                range.getPosA().getD1(), range.getPosB().getD1(),
                range.getPosA().getD2(), range.getPosB().getD2()
        );
    }

    @Override
    public FPos3D produce() {

        return factoryExt.getFPos3D(
                this.random.nextDouble(d0min, d0max),
                this.random.nextDouble(d1min, d1max),
                this.random.nextDouble(d2min, d2max)
        );
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.random.nextDouble(d0min, d0max);
        in[1] = this.random.nextDouble(d1min, d1max);
        in[2] = this.random.nextDouble(d2min, d2max);
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
