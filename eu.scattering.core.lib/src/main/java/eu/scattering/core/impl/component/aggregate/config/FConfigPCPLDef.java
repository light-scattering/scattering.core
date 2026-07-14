package eu.scattering.core.impl.component.aggregate.config;

import eu.scattering.core.design.component.aggregate.config.df.kinetic.pc.FConfigPCPL;

public class FConfigPCPLDef implements FConfigPCPL {
    private double ratioWindow = 0.9;
    private double ratioDrop = 0;

    private FConfigPCPLDef() {}

    public static FConfigPCPL create() {

        return new FConfigPCPLDef();
    }

    public static FConfigPCPL create(Preset preset) {
        FConfigPCPL config = create();

        switch (preset) {
            case WINDOW -> config
                    .setWindowRatio(0.9)
                    .setDropRatio(0.0);
            case DROP -> config
                    .setWindowRatio(1.0)
                    .setDropRatio(0.3);
        }

        return config;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public double getWindowRatio() {

        return this.ratioWindow;
    }

    @Override
    public FConfigPCPL setWindowRatio(double ratio) {

        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("The ratio must be between zero and one");
        }

        this.ratioWindow = ratio;

        return this;
    }

    @Override
    public double getDropRatio() {

        return this.ratioDrop;
    }

    @Override
    public FConfigPCPL setDropRatio(double ratio) {

        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("The ratio must be between zero and one");
        }

        this.ratioDrop = ratio;

        return this;
    }
}
