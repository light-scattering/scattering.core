package eu.scattering.core.impl.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.module.dist1d.fixed.FDist1DFixed;

public class FDist1DFixedDef implements FDist1DFixed {
    private final double x;

    private FDist1DFixedDef(double x) {

        this.x = x;
    }

    public static FDist1DFixed get(double x) {

        return new FDist1DFixedDef(x);
    }

    @Override
    public double produce() {

        return this.x;
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.x;
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
