package eu.scattering.core.impl.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.uniform.FDist3DUniform;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

public class FDist3DUniformDef implements FDist3DUniform {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private final FRandGenerator random;
    private final double x1, x2, y1, y2, z1, z2;

    private FDist3DUniformDef(FRandGenerator random, double x1, double x2, double y1, double y2, double z1, double z2) {

        this.random = random;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }

    public static FDist3DUniform get(FRandGenerator random, double x1, double x2, double y1, double y2, double z1, double z2) {

        return new FDist3DUniformDef(random, x1, x2, y1, y2, z1, z2);
    }

    public static FDist3DUniform get(FRandGenerator random, FPairPos3D range) {

        return new FDist3DUniformDef(random,
                range.getPosA().getD0(), range.getPosB().getD0(),
                range.getPosA().getD1(), range.getPosB().getD1(),
                range.getPosA().getD2(), range.getPosB().getD2()
        );
    }

    @Override
    public FPos3D produce() {

        return factory.getFPos3D(
                this.random.nextDouble(x1, x2),
                this.random.nextDouble(y1, y2),
                this.random.nextDouble(z1, z2)
        );
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.random.nextDouble(x1, x2);
        in[1] = this.random.nextDouble(y1, y2);
        in[2] = this.random.nextDouble(z1, z2);
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
