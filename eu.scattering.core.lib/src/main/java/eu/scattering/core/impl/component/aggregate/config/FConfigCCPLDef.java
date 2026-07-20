package eu.scattering.core.impl.component.aggregate.config;

import eu.scattering.core.design.component.aggregate.config.df.kinetic.cc.FConfigCCPL;
import eu.scattering.core.design.statistics.base.FStat;

import java.util.function.Function;

public class FConfigCCPLDef implements FConfigCCPL {
    private Function<FStat, Double> reducer = FStat::mean;
    private double ratioWindow = 0.9;
    private double ratioDrop = 0;

    private FConfigCCPLDef() {}

    public static FConfigCCPL create() {

        return new FConfigCCPLDef();
    }

    public static FConfigCCPL create(FConfigCCPL.Preset preset) {
        FConfigCCPL config = create();

        switch (preset) {
            case WINDOW -> config
                    .setReducer(FStat::mean)
                    .setWindowRatio(0.9)
                    .setDropRatio(0.0);
            case DROP -> config
                    .setReducer(FStat::mean)
                    .setWindowRatio(1.0)
                    .setDropRatio(0.3);
        }

        return config;
    }

// -------------------------------------------------------------------------------------------------


    @Override
    public Function<FStat, Double> getReducer() {

        return this.reducer;
    }

    @Override
    public FConfigCCPL setReducer(Function<FStat, Double> reducer) {

        if (reducer == null) {
            throw new IllegalArgumentException("The reducer cannot be null");
        }

        this.reducer = reducer;

        return this;
    }

    @Override
    public double getWindowRatio() {

        return this.ratioWindow;
    }

    @Override
    public FConfigCCPL setWindowRatio(double ratio) {

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
    public FConfigCCPL setDropRatio(double ratio) {

        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("The ratio must be between zero and one");
        }

        this.ratioDrop = ratio;

        return this;
    }
}
