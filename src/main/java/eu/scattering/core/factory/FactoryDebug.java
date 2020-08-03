package eu.scattering.core.factory;

import eu.scattering.core.debug.IStats;
import eu.scattering.core.debug.IStatsMethod;
import eu.scattering.core.debug.impl.Stats;
import eu.scattering.core.debug.impl.StatsMethod;

public class FactoryDebug {

    private FactoryDebug() { }

    // -------------------------------------------------------------------------------------------------
    // The following section contains methods associated with the debugging feature, they should not be changed.
    // -------------------------------------------------------------------------------------------------

    public static IStats getIStats() {

        return Stats.create();
    }

    public static IStatsMethod getIStatsMethod() {

        return StatsMethod.create();
    }
}
