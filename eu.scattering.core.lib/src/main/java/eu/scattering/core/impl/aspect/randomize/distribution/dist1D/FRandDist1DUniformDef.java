package eu.scattering.core.impl.aspect.randomize.distribution.dist1D;

import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.uniform.FRandDist1DUniform;

public class FRandDist1DUniformDef implements FRandDist1DUniform {
    private final FRandEngine random;
    private final double x1, x2;

    private FRandDist1DUniformDef(FRandEngine random, double x1, double x2) {

        this.random = random;
        this.x1 = x1;
        this.x2 = x2;
    }

    public static FRandDist1DUniform get(FRandEngine random, double x1, double x2) {

        return new FRandDist1DUniformDef(random, x1, x2);
    }

    @Override
    public double produce() {

        return this.random.nextDouble(x1, x2);
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.random.nextDouble(x1, x2);
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
