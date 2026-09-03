package eu.scattering.core.impl.aspect.randomize.distribution.dist1D;

import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.normal.FRandDist1DNormal;

public class FRandDist1DNormalDef implements FRandDist1DNormal {
    private final FRandEngine random;
    private final double mean;
    private final double std;

    private double cMin = Double.NEGATIVE_INFINITY;
    private double cMax = Double.POSITIVE_INFINITY;

    private FRandDist1DNormalDef(FRandEngine random, double mean, double std) {

        if (std <= 0) {
            throw new IllegalArgumentException("The std value must be greater than zero");
        }

        this.random = random;
        this.mean = mean;
        this.std = std;
    }

    public static FRandDist1DNormal get(FRandEngine random, double x1, double x2) {

        return new FRandDist1DNormalDef(random, x1, x2);
    }

    @Override
    public double produce() {
        double value = this.random.nextGaussian(this.mean, this.std);

        while (value < this.cMin || value > this.cMax) {
            value = this.random.nextGaussian(this.mean, this.std);
        }

        return value;
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = produce();
    }

    @Override
    public double getCutoffMin() {

        return this.cMin;
    }

    @Override
    public void setCutoffMin(double cutoff) {

        if (cutoff >= this.cMax) {
            throw new IllegalArgumentException("The min cutoff must not be greater than the max cutoff");
        }

        this.cMin = cutoff;
    }

    @Override
    public double getCutoffMax() {

        return this.cMax;
    }

    @Override
    public void setCutoffMax(double cutoff) {

        if (cutoff <= this.cMin) {
            throw new IllegalArgumentException("The max cutoff must not be smaller then the min cutoff");
        }

        this.cMax = cutoff;
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
