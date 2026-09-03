package eu.scattering.core.impl.aspect.randomize.distribution.dist2D;

import eu.scattering.core.design.aspect.randomize.distribution.dist2d.fixed.FRandDist2DFixed;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public class FRandDist2DFixedDef implements FRandDist2DFixed {
    private final TransferFactory factoryExt;

    private final double d0, d1;

    private FRandDist2DFixedDef(TransferFactory factoryExt, double d0, double d1) {
        this.factoryExt = factoryExt;

        this.d0 = d0;
        this.d1 = d1;
    }

    public static FRandDist2DFixed create(TransferFactory factoryExt, double d0, double d1) {

        return new FRandDist2DFixedDef(factoryExt, d0, d1);
    }

    public static FRandDist2DFixed create(TransferFactory factoryExt, FPos2D val) {

        return new FRandDist2DFixedDef(factoryExt, val.getD0(), val.getD1());
    }

    @Override
    public FPos2D produce() {

        return factoryExt.getFPos2D(this.d0, this.d1);
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.d0;
        in[1] = this.d1;
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
