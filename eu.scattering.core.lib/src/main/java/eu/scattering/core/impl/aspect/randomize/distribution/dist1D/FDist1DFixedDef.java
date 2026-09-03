package eu.scattering.core.impl.aspect.randomize.distribution.dist1D;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.fixed.FDist1DFixed;

public class FDist1DFixedDef implements FDist1DFixed {
    private final double d0;

    private FDist1DFixedDef(double d0) {

        this.d0 = d0;
    }

    public static FDist1DFixed get(double d0) {

        return new FDist1DFixedDef(d0);
    }

    @Override
    public double produce() {

        return this.d0;
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.d0;
    }

    private void validate(double[] in) {

        if (in == null) {
            throw new NullPointerException("The input array is null");
        }

        if (in.length < 1) {
            throw new IllegalArgumentException("The input array does not contain the required number of elements");
        }
    }
}
