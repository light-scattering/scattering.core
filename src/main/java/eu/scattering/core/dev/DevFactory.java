package eu.scattering.core.dev;

import eu.scattering.core.dev.stats.IStats;
import eu.scattering.core.dev.stats.IStatsMethod;
import eu.scattering.core.dev.stats.impl.Stats;
import eu.scattering.core.dev.stats.impl.StatsMethod;

public class DevFactory {

    private DevFactory() { }

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
