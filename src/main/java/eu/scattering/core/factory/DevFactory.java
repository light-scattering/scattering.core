package eu.scattering.core.factory;

import eu.scattering.core.logic.dev.stats.Stats;
import eu.scattering.core.logic.dev.stats.StatsMethod;
import eu.scattering.core.impl.dev.stats.StatsDefault;
import eu.scattering.core.impl.dev.stats.StatsMethodDefault;

public class DevFactory {

    private DevFactory() { }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods associated with the debugging feature, they should not be changed.
    // -------------------------------------------------------------------------------------------------

    public static Stats getIStats(boolean global) {

        return StatsDefault.create(global);
    }

    public static StatsMethod getIStatsMethod() {

        return StatsMethodDefault.create();
    }
}
