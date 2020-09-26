package eu.scattering.core.design.development;

import eu.scattering.core.design.development.statistics.Statistics;

import java.util.Optional;

public interface Development<T> {

    boolean devIsStatisticsEnabled();
    T devSetStatisticsEnabled(boolean enabled);

    T devDesc();
    T devDescStatistics();
    T devDescClassStatistics();
    T devDescNumberOfInstances();

    Optional<Long> devGetNumberOfInstances();
    T devResetNumberOfInstances();

    Optional<Statistics> devGetStatistics();
    Optional<Statistics> devGetClassStatistics();

    String devGetLabel();
    T devSetLabel(String label);
}
