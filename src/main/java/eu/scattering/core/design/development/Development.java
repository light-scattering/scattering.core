package eu.scattering.core.design.development;

import eu.scattering.core.design.development.statistics.Statistics;

import java.util.Optional;

public interface Development<T> {

    T devDescribe();
    T devDescribeStats();
    T devDescribeClassStats();

    Optional<Long> devGetNumberOfInstances();
    T devResetNumberOfInstances();

    Optional<Statistics> devGetStats();
    Optional<Statistics> devGetClassStats();

    String devGetMeta();
    T devSetMeta(String meta);
}
