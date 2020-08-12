package eu.scattering.core.factory;

import eu.scattering.core.debug.stats.IStats;
import eu.scattering.core.debug.stats.IStatsMethod;
import eu.scattering.core.debug.stats.impl.Stats;
import eu.scattering.core.debug.stats.impl.StatsMethod;

public class FactoryDebug {

    private FactoryDebug() { }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods associated with the debugging feature, they should not be changed.
    // -------------------------------------------------------------------------------------------------

    public static IStats getIStats(boolean global) {

        return Stats.create(global);
    }

    public static IStatsMethod getIStatsMethod() {

        return StatsMethod.create();
    }
}
