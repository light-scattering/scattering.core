package eu.scattering.core.impl.component.aggregate.config;

import eu.scattering.core.design.component.aggregate.config.df.FConfigBC;

public class FConfigBCDef implements FConfigBC {
    private int shiftsPerAxis = 3;
    private double ratioWindow = 0.9;
    private double scalingFactor = 2.0;
    private boolean isAlignedPCA = true;
    private boolean isAlignedOrigin = false;

    private FConfigBCDef() {}

    public static FConfigBC create() {

        return new FConfigBCDef();
    }

    public static FConfigBC create(Preset preset) {
        FConfigBC config = create();

        switch (preset) {
            case OPTIMIZED, MAN_07072026_SHIFT_PCA ->  {}
            case RAW -> config
                    .setWindowRatio(1);
            case BASELINE -> config
                    .setShiftsPerAxis(1)
                    .setAlignedPCA(false)
                    .setAlignedOrigin(true)
                    .setWindowRatio(1);
            case MAN_07072026_BASELINE ->  config
                    .setShiftsPerAxis(1)
                    .setAlignedPCA(false);
            case MAN_07072026_PCA ->  config
                    .setShiftsPerAxis(1);
            case MAN_07072026_SHIFT -> config
                    .setAlignedPCA(false);
            case MAN_07072026_SHIFT_STEP -> config
                    .setScalingFactor(1.25)
                    .setAlignedPCA(false);
            case MAN_07072026_SHIFT_PCA_STEP -> config
                    .setScalingFactor(1.25);
        }

        return config;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int getShiftsPerAxis() {

        return this.shiftsPerAxis;
    }

    @Override
    public FConfigBC setShiftsPerAxis(int shifts) {

        if (shifts < 1) {
            throw new IllegalArgumentException("The number of shifts must be greater than zero");
        }

        this.shiftsPerAxis = shifts;

        return this;
    }

    @Override
    public double getScalingFactor() {

        return this.scalingFactor;
    }

    @Override
    public FConfigBC setScalingFactor(double factor) {

        if (factor <= 1) {
            throw new IllegalArgumentException("The scaling factor must be greater than one");
        }

        this.scalingFactor = factor;

        return this;
    }

    @Override
    public boolean isAlignedOrigin() {

        return this.isAlignedOrigin;
    }

    @Override
    public FConfigBC setAlignedOrigin(boolean isAligned) {

        this.isAlignedOrigin = isAligned;

        return this;
    }

    @Override
    public boolean isAlignedPCA() {

        return this.isAlignedPCA;
    }

    @Override
    public FConfigBC setAlignedPCA(boolean isAligned) {

        this.isAlignedPCA = isAligned;

        return this;
    }

    @Override
    public double getWindowRatio() {

        return this.ratioWindow;
    }

    @Override
    public FConfigBC setWindowRatio(double ratio) {

        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("The ratio must be between zero and one");
        }

        this.ratioWindow = ratio;

        return this;
    }
}
