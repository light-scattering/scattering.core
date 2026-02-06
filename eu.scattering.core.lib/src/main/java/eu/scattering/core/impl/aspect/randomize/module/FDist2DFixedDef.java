package eu.scattering.core.impl.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.fixed.FDist2DFixed;
import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import eu.scattering.core.design.transfer.primitive.FPos2D;

public class FDist2DFixedDef implements FDist2DFixed {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private final double x, y;

    private FDist2DFixedDef(double x, double y) {

        this.x = x;
        this.y = y;
    }

    public static FDist2DFixed get(double x, double y) {

        return new FDist2DFixedDef(x, y);
    }

    public static FDist2DFixed get(FPos2D val) {

        return new FDist2DFixedDef(val.getD0(), val.getD1());
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
