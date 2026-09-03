package eu.scattering.core.impl.aspect.randomize.distribution.dist1D;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.fixed.FRandDist1DFixed;

public class FRandDist1DFixedDef implements FRandDist1DFixed {
    private final double d0;

    private FRandDist1DFixedDef(double d0) {

        this.d0 = d0;
    }

    public static FRandDist1DFixed get(double d0) {

        return new FRandDist1DFixedDef(d0);
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
