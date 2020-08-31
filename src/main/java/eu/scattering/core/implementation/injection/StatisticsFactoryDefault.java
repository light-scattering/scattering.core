package eu.scattering.core.implementation.injection;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.development.statistics.StatisticsMethod;
import eu.scattering.core.design.injection.StatisticsFactory;
import eu.scattering.core.implementation.development.statistics.StatisticsDefault;
import eu.scattering.core.implementation.development.statistics.StatisticsMethodDefault;

public final class StatisticsFactoryDefault implements StatisticsFactory {

    @Override
    public Statistics getStatistics() {

        return StatisticsDefault.create();
    }
    @Override
    public StatisticsMethod getStatisticsMethod() {

        return StatisticsMethodDefault.create();
    }
}
