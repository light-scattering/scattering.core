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

    private double corXY = 0, corXZ = 0, corYZ = 0;

    private double avgX, avgY, avgZ;
    private double stdX, stdY, stdZ;

    private boolean isDirty = true;

    private FDist3DNormalDef(TransferFactory factoryExt, FRandGenerator random, double avg, double std) {

        if (std <= 0) {
            throw new IllegalArgumentException("The std value must be greater than zero");
        }

        this.factoryExt = factoryExt;
        this.random = random;

        this.avgX = avg;
        this.avgY = avg;
        this.avgZ = avg;

        this.stdX = std;
        this.stdY = std;
        this.stdZ = std;
    }

    public static FDist3DNormal create(TransferFactory factoryExt, FRandGenerator random, double avg, double std) {

        return new FDist3DNormalDef(factoryExt, random, avg, std);
    }

    @Override
    public FDist3DNormal setAvg(double avgX, double avgY, double avgZ) {

        this.avgX = avgX;
        this.avgY = avgY;
        this.avgZ = avgZ;

        return this;
    }

    @Override
    public FDist3DNormal setStd(double stdX, double stdY, double stdZ) {

        if (stdX <= 0) {
            throw new IllegalArgumentException("The std X value must be greater than zero");
        }

        if (stdY <= 0) {
            throw new IllegalArgumentException("The std Y value must be greater than zero");
        }

        if (stdZ <= 0) {
            throw new IllegalArgumentException("The std Z value must be greater than zero");
        }

        this.stdX = stdX;
        this.stdY = stdY;
        this.stdZ = stdZ;

        this.isDirty = true;

        return this;
    }

    @Override
    public FDist3DNormal setCorXY(double corXY) {

        if (corXY < -1.0 || corXY > 1.0) {
            throw new IllegalArgumentException("Correlation must be between -1.0 and 1.0");
        }

        this.corXY = corXY;

        this.isDirty = true;

        return this;
    }

    @Override
    public FDist3DNormal setCorXZ(double corXZ) {

        if (corXZ < -1.0 || corXZ > 1.0) {
            throw new IllegalArgumentException("Correlation must be between -1.0 and 1.0");
        }

        this.corXZ = corXZ;

        this.isDirty = true;

        return this;
    }

    @Override
    public FDist3DNormal setCorYZ(double corYZ) {

        if (corYZ < -1.0 || corYZ > 1.0) {
            throw new IllegalArgumentException("Correlation must be between -1.0 and 1.0");
        }

        this.corYZ = corYZ;

        this.isDirty = true;

        return this;
    }

    @Override
    public FPos3D produce() {

        if (this.isDirty) {
            getTransMatrix();
        }

        double x = random.nextGaussian();
        double y = random.nextGaussian();
        double z = random.nextGaussian();

        return factoryExt.getFPos3D(
                this.avgX + trans[0][0] * x,
                this.avgY + trans[1][0] * x + trans[1][1] * y,
                this.avgZ + trans[2][0] * x + trans[2][1] * y + trans[2][2] * z
        );
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        if (this.isDirty) {
            getTransMatrix();
        }

        double x = random.nextGaussian();
        double y = random.nextGaussian();
        double z = random.nextGaussian();

        in[0] = this.avgX + trans[0][0] * x;
        in[1] = this.avgY + trans[1][0] * x + trans[1][1] * y;
        in[2] = this.avgZ + trans[2][0] * x + trans[2][1] * y + trans[2][2] * z;
    }

    @LLM
    private void getTransMatrix() {
        double[][] cov = new double[3][3];

        cov[0][0] = this.stdX * this.stdX;
        cov[1][1] = this.stdY * this.stdY;
        cov[2][2] = this.stdZ * this.stdZ;

        cov[0][1] = this.corXY * this.stdX * this.stdY;
        cov[1][0] = cov[0][1];

        cov[0][2] = this.corXZ * this.stdX * this.stdZ;
        cov[2][0] = cov[0][2];

        cov[1][2] = this.corYZ * this.stdY * this.stdZ;
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
}
