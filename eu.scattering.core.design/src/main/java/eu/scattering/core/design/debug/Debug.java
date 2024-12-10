package eu.scattering.core.design.debug;

import eu.scattering.core.design.debug.stats.Stats;

import java.util.Optional;

public interface Debug<T> {

    boolean devIsStatisticsEnabled();
    T devSetStatisticsEnabled(boolean enabled);

    T devDesc();
    T devDescStatistics();
    T devDescClassStatistics();
    T devDescNumberOfInstances();

    Optional<Long> devGetNumberOfInstances();
    T devResetNumberOfInstances();

    Optional<Stats> devGetStatistics();
    Optional<Stats> devGetClassStatistics();

    String devGetLabel();
    T devSetLabel(String label);
}
