package eu.scattering.core.dev;

import eu.scattering.core.dev.stats.IStats;

import java.util.Optional;

public interface IDev<T> {

    T devDescribe();
    T devDescribeStats();
    T devDescribeClassStats();

    Optional<Long> devGetNumberOfInstances();
    T devResetNumberOfInstances();

    Optional<IStats> devGetStats();
    Optional<IStats> devGetClassStats();

    String devGetMeta();
    T devSetMeta(String meta);
}
