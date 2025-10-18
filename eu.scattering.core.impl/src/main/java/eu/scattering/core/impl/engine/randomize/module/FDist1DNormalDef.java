package eu.scattering.core.impl.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.normal.FDist1DNormal;

public class FDist1DNormalDef implements FDist1DNormal {
    private final FRandGenerator random;
    private final double mean;
    private final double std;

    private double cMin = Double.NEGATIVE_INFINITY;
    private double cMax = Double.POSITIVE_INFINITY;

    private FDist1DNormalDef(FRandGenerator random, double mean, double std) {

        if (std <= 0) {
            throw new IllegalArgumentException("The std value must be greater than zero");
        }

        this.random = random;
        this.mean = mean;
        this.std = std;
    }

    public static FDist1DNormal get(FRandGenerator random, double x1, double x2) {

        return new FDist1DNormalDef(random, x1, x2);
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
