package eu.scattering.core.design.injection;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.development.statistics.StatisticsMethod;

public interface StatisticsFactory {

    Statistics getStatistics();

    StatisticsMethod getStatisticsMethod();
}
