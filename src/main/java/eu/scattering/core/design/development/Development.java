package eu.scattering.core.design.development;

import eu.scattering.core.design.development.statistics.Statistics;

import java.util.Optional;

public interface Development<T> {

    T devDescribe();
    T devDescribeStatistics();
    T devDescribeClassStatistics();
//    T devDescribeNumberOfInstances();

    Optional<Long> devGetNumberOfInstances();
    T devResetNumberOfInstances();

    T objectStatisticsEnable();
    T objectStatisticsDisable();

    Optional<Statistics> devGetStatistics();
    Optional<Statistics> devGetClassStatistics();

    String devGetLabel();
    T devSetLabel(String label);
}
