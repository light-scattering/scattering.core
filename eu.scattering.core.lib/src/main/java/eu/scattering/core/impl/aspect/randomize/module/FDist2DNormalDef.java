package eu.scattering.core.impl.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.normal.FDist2DNormal;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import eu.scattering.core.design.utility.annotation.LLM;

public class FDist2DNormalDef implements FDist2DNormal {
    private final TransferFactory factoryExt;
    private final FRandGenerator random;

    private final double[][] trans = new double[2][2];

    private double cor = 0;

    private double avgD0, avgD1;
    private double stdD0, stdD1;

    private boolean isDirty = true;

    private FDist2DNormalDef(TransferFactory factoryExt, FRandGenerator random, double avg, double std) {

        if (std <= 0) {
            throw new IllegalArgumentException("The std value must be greater than zero");
        }

        this.factoryExt = factoryExt;
        this.random = random;

        this.avgD0 = avg;
        this.avgD1 = avg;

        this.stdD0 = std;
        this.stdD1 = std;
    }

    public static FDist2DNormal create(TransferFactory factoryExt, FRandGenerator random, double avg, double std) {

        return new FDist2DNormalDef(factoryExt, random, avg, std);
    }

    @Override
    public FDist2DNormal setAvg(double d0, double d1) {

        this.avgD0 = d0;
        this.avgD1 = d1;

        return this;
    }

    @Override
    public FDist2DNormal setStd(double d0, double d1) {

        if (d0 <= 0) {
            throw new IllegalArgumentException("The std d0 value must be greater than zero");
        }

        if (d1 <= 0) {
            throw new IllegalArgumentException("The std d1 value must be greater than zero");
        }

        this.stdD0 = d0;
        this.stdD1 = d1;

        this.isDirty = true;

        return this;
    }

    @Override
    public FDist2DNormal setCor(double d01) {

        if (d01 < -1.0 || d01 > 1.0) {
            throw new IllegalArgumentException("Correlation must be between -1.0 and 1.0");
        }

        this.cor = d01;

        this.isDirty = true;

        return this;
    }

    @Override
    public FPos2D produce() {

        if (this.isDirty) {
            getTransMatrix();
        }

        double d0 = random.nextGaussian();
        double d1 = random.nextGaussian();

        return factoryExt.getFPos2D(
                this.avgD0 + trans[0][0] * d0,
                this.avgD1 + trans[1][0] * d0 + trans[1][1] * d1
        );
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        if (this.isDirty) {
            getTransMatrix();
        }

        double d0 = random.nextGaussian();
        double d1 = random.nextGaussian();

        in[0] = this.avgD0 + trans[0][0] * d0;
        in[1] = this.avgD1 + trans[1][0] * d0 + trans[1][1] * d1;
    }

    @LLM
    private void getTransMatrix() {

        this.trans[0][0] = this.stdD0;
        this.trans[1][0] = this.cor * this.stdD1;
        this.trans[1][1] = this.stdD1 * Math.sqrt(1.0 - (this.cor * this.cor));

        this.isDirty = false;
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
