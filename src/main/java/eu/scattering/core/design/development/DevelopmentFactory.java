package eu.scattering.core.design.development;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.development.statistics.StatisticsMethod;

public interface DevelopmentFactory {

    Statistics getStatistics();

    StatisticsMethod getStatisticsMethod();
}
