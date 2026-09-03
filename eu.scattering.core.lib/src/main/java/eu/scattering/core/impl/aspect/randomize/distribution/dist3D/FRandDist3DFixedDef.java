package eu.scattering.core.impl.aspect.randomize.distribution.dist3D;

import eu.scattering.core.design.aspect.randomize.distribution.dist3d.fixed.FRandDist3DFixed;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public class FRandDist3DFixedDef implements FRandDist3DFixed {
    private final TransferFactory factoryExt;

    private final double d0, d1, d2;

    private FRandDist3DFixedDef(TransferFactory factoryExt, double d0, double d1, double d2) {
        this.factoryExt = factoryExt;

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
    }

    public static FRandDist3DFixed create(TransferFactory factoryExt, double d0, double d1, double d2) {

        return new FRandDist3DFixedDef(factoryExt, d0, d1, d2);
    }

    public static FRandDist3DFixed create(TransferFactory factoryExt, FPos3D val) {

        return new FRandDist3DFixedDef(factoryExt, val.getD0(), val.getD1(), val.getD2());
    }

    @Override
    public FPos3D produce() {

        return factoryExt.getFPos3D(this.d0, this.d1, this.d2);
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.d0;
        in[1] = this.d1;
        in[2] = this.d2;
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
