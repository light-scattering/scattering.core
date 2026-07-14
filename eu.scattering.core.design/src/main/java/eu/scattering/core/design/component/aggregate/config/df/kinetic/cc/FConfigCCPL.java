package eu.scattering.core.design.component.aggregate.config.df.kinetic.cc;

import eu.scattering.core.design.statistics.base.FStat;

import java.util.function.Function;

public interface FConfigCCPL {

    double getWindowRatio();
    FConfigCCPL setWindowRatio(double ratio);

    double getDropRatio();
    FConfigCCPL setDropRatio(double ratio);

    Function<FStat, Double> getReducer();
    FConfigCCPL setReducer(Function<FStat, Double> reducer);

    // -------------------------------------------------------------------------------------------------

    enum Preset {
        WINDOW, DROP
    }
}
