package eu.scattering.core.impl.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.fixed.FDist2DFixed;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public class FDist2DFixedDef implements FDist2DFixed {
    private final TransferFactory factoryExt;

    private final double x, y;

    private FDist2DFixedDef(TransferFactory factoryExt, double x, double y) {
        this.factoryExt = factoryExt;

        this.x = x;
        this.y = y;
    }

    public static FDist2DFixed create(TransferFactory factoryExt, double x, double y) {

        return new FDist2DFixedDef(factoryExt, x, y);
    }

    public static FDist2DFixed create(TransferFactory factoryExt, FPos2D val) {

        return new FDist2DFixedDef(factoryExt, val.getD0(), val.getD1());
    }

    @Override
    public FPos2D produce() {

        return factoryExt.getFPos2D(this.x, this.y);
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.x;
        in[1] = this.y;
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
