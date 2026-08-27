package eu.scattering.core.impl.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.normal.FDist3DNormal;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.annotation.LLM;

public class FDist3DNormalDef implements FDist3DNormal {
    private final TransferFactory factoryExt;
    private final FRandGenerator random;

    private final double[][] trans = new double[3][3];

    private double corD01 = 0, corD02 = 0, corD12 = 0;

    private double avgD0, avgD1, avgD2;
    private double stdD0, stdD1, stdD2;

    private boolean isDirty = true;

    private FDist3DNormalDef(TransferFactory factoryExt, FRandGenerator random, double avg, double std) {

        if (std <= 0) {
            throw new IllegalArgumentException("The std value must be greater than zero");
        }

        this.factoryExt = factoryExt;
        this.random = random;

        this.avgD0 = avg;
        this.avgD1 = avg;
        this.avgD2 = avg;

        this.stdD0 = std;
        this.stdD1 = std;
        this.stdD2 = std;
    }

    public static FDist3DNormal create(TransferFactory factoryExt, FRandGenerator random, double avg, double std) {

        return new FDist3DNormalDef(factoryExt, random, avg, std);
    }

    @Override
    public FDist3DNormal setAvg(double avgD0, double avgD1, double avgD2) {

        this.avgD0 = avgD0;
        this.avgD1 = avgD1;
        this.avgD2 = avgD2;

        return this;
    }

    @Override
    public FDist3DNormal setStd(double stdD0, double stdD1, double stdD2) {

        if (stdD0 <= 0) {
            throw new IllegalArgumentException("The std X value must be greater than zero");
        }

        if (stdD1 <= 0) {
            throw new IllegalArgumentException("The std Y value must be greater than zero");
        }

        if (stdD2 <= 0) {
            throw new IllegalArgumentException("The std Z value must be greater than zero");
        }

        this.stdD0 = stdD0;
        this.stdD1 = stdD1;
        this.stdD2 = stdD2;

        this.isDirty = true;

        return this;
    }

    @Override
    public FDist3DNormal setCorD01(double corD01) {

        if (corD01 < -1.0 || corD01 > 1.0) {
            throw new IllegalArgumentException("Correlation must be between -1.0 and 1.0");
        }

        this.corD01 = corD01;

        this.isDirty = true;

        return this;
    }

    @Override
    public FDist3DNormal setCorD02(double corD02) {

        if (corD02 < -1.0 || corD02 > 1.0) {
            throw new IllegalArgumentException("Correlation must be between -1.0 and 1.0");
        }

        this.corD02 = corD02;

        this.isDirty = true;

        return this;
    }

    @Override
    public FDist3DNormal setCorD12(double corD12) {

        if (corD12 < -1.0 || corD12 > 1.0) {
            throw new IllegalArgumentException("Correlation must be between -1.0 and 1.0");
        }

        this.corD12 = corD12;

        this.isDirty = true;

        return this;
    }

    @Override
    public FPos3D produce() {

        if (this.isDirty) {
            getTransMatrix();
        }

        double d0 = random.nextGaussian();
        double d1 = random.nextGaussian();
        double d2 = random.nextGaussian();

        return factoryExt.getFPos3D(
                this.avgD0 + trans[0][0] * d0,
                this.avgD1 + trans[1][0] * d0 + trans[1][1] * d1,
                this.avgD2 + trans[2][0] * d0 + trans[2][1] * d1 + trans[2][2] * d2
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
        double d2 = random.nextGaussian();

        in[0] = this.avgD0 + trans[0][0] * d0;
        in[1] = this.avgD1 + trans[1][0] * d0 + trans[1][1] * d1;
        in[2] = this.avgD2 + trans[2][0] * d0 + trans[2][1] * d1 + trans[2][2] * d2;
    }

    @LLM
    private void getTransMatrix() {

        validateCorrelation();

        double[][] cov = new double[3][3];

        cov[0][0] = this.stdD0 * this.stdD0;
        cov[1][1] = this.stdD1 * this.stdD1;
        cov[2][2] = this.stdD2 * this.stdD2;

        cov[0][1] = this.corD01 * this.stdD0 * this.stdD1;
        cov[1][0] = cov[0][1];

        cov[0][2] = this.corD02 * this.stdD0 * this.stdD2;
        cov[2][0] = cov[0][2];

        cov[1][2] = this.corD12 * this.stdD1 * this.stdD2;
        cov[2][1] = cov[1][2];

        this.trans[0][0] = Math.sqrt(cov[0][0]);
        this.trans[1][0] = cov[1][0] / this.trans[0][0];
        this.trans[2][0] = cov[2][0] / this.trans[0][0];

        this.trans[1][1] = Math.sqrt(cov[1][1] - this.trans[1][0] * this.trans[1][0]);
        this.trans[2][1] = (cov[2][1] - this.trans[2][0] * this.trans[1][0]) / this.trans[1][1];

        this.trans[2][2] = Math.sqrt(cov[2][2] - (this.trans[2][0] * this.trans[2][0]) - (this.trans[2][1] * this.trans[2][1]));

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

    private void validateCorrelation() {
        double condition = (corD01 * corD01) + (corD12 * corD12) + (corD12 * corD12) - (2 * corD01 * corD02 * corD12);

        if (condition > 1) {
            throw new IllegalStateException("The correlation is invalid");
        }
    }
}
