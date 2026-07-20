package eu.scattering.core.impl.component.aggregate.config;

import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigMR;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

public class FConfigMRDef implements FConfigMR {
    private RadiusOfGyration radiusOfGyration = RadiusOfGyration.SIMPLE_POLY;
    private double ratioWindow = 0.9;
    private double scalingFactor = 1.1;
    private boolean isRestricted = true;

    private FConfigMRDef() {}

    public static FConfigMR create() {

        return new FConfigMRDef();
    }

    public static FConfigMR create(FConfigMR.Preset preset) {
        FConfigMR config = create();

        switch (preset) {
            case RESTRICTED -> config
                    .setWindowRatio(0.9)
                    .setScalingFactor(1.1)
                    .setRestricted(true);
            case FULL -> config
                    .setWindowRatio(0.9)
                    .setScalingFactor(1.1)
                    .setRestricted(false);
        }

        return config;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public RadiusOfGyration getRadiusOfGyration() {

        return this.radiusOfGyration;
    }

    @Override
    public FConfigMR setRadiusOfGyration(RadiusOfGyration type) {

        if (type == null) {
            throw new IllegalArgumentException("The radius of gyration type cannot be null");
        }

        this.radiusOfGyration = type;

        return this;
    }

    @Override
    public double getScalingFactor() {

        return this.scalingFactor;
    }

    @Override
    public FConfigMR setScalingFactor(double factor) {

        if (factor <= 1) {
            throw new IllegalArgumentException("The scaling factor must be greater than one");
        }

        this.scalingFactor = factor;

        return this;
    }

    @Override
    public boolean isRestricted() {

        return this.isRestricted;
    }

    @Override
    public FConfigMR setRestricted(boolean restricted) {

        this.isRestricted = restricted;

        return this;
    }

    @Override
    public double getWindowRatio() {

        return this.ratioWindow;
    }

    @Override
    public FConfigMR setWindowRatio(double ratio) {

        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("The ratio must be between zero and one");
        }

        this.ratioWindow = ratio;

        return this;
    }
}
