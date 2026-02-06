package eu.scattering.core.impl.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.fixed.FDist3DFixed;
import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import eu.scattering.core.design.transfer.primitive.FPos3D;

public class FDist3DFixedDef implements FDist3DFixed {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private final double x, y, z;

    private FDist3DFixedDef(double x, double y, double z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static FDist3DFixed get(double x, double y, double z) {

        return new FDist3DFixedDef(x, y, z);
    }

    public static FDist3DFixed get(FPos3D val) {

        return new FDist3DFixedDef(val.getD0(), val.getD1(), val.getD2());
    }

    @Override
    public FPos3D produce() {

        return factoryExt.getFPos3D(this.x, this.y, this.z);
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.x;
        in[1] = this.y;
        in[2] = this.z;
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
