package eu.scattering.core.impl.component.aggregate.config;

import eu.scattering.core.design.component.aggregate.config.df.FConfigDC;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

public class FConfigDCDef implements FConfigDC {
    private RadiusOfGyration radiusOfGyration = RadiusOfGyration.SIMPLE_POLY;
    private double ratioWindow = 0.9;
    private double scalingFactor = 1.1;
    private boolean isRestricted = true;

    private FConfigDCDef() {}

    public static FConfigDC create() {

        return new FConfigDCDef();
    }

    public static FConfigDC create(Preset preset) {
        FConfigDC config = create();

        switch (preset) {
            case RESTRICTED -> {}
            case FULL -> config.setRestricted(false);
        }

        return config;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public RadiusOfGyration getRadiusOfGyration() {

        return this.radiusOfGyration;
    }

    @Override
    public FConfigDC setRadiusOfGyration(RadiusOfGyration type) {

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
    public FConfigDC setScalingFactor(double factor) {

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
    public FConfigDC setRestricted(boolean restricted) {

        this.isRestricted = restricted;

        return this;
    }

    @Override
    public double getWindowRatio() {

        return this.ratioWindow;
    }

    @Override
    public FConfigDC setWindowRatio(double ratio) {

        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("The ratio must be between zero and one");
        }

        this.ratioWindow = ratio;

        return this;
    }
}
