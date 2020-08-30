package eu.scattering.core.injection;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.development.statistics.StatisticsMethod;
import eu.scattering.core.implementation.development.statistics.StatisticsDefault;
import eu.scattering.core.implementation.development.statistics.StatisticsMethodDefault;

public class DevelopmentFactory {

    private DevelopmentFactory() { }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods associated with the debugging feature, they should not be changed.
    // -------------------------------------------------------------------------------------------------

    public static Statistics getIStats() {

        return StatisticsDefault.create();
    }

    public static StatisticsMethod getIStatsMethod() {

        return StatisticsMethodDefault.create();
    }
}
