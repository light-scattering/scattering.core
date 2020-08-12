package eu.scattering.core.debug;

import eu.scattering.core.debug.stats.IStats;

import java.util.Optional;

public interface IDev<T> {

    T devDescribe();
    T devDescribeStats();
    T devDescribeClassStats();

    Optional<Long> devGetNumberOfInstances();
//    void devResetNumberOfInstances();

    Optional<IStats> devGetStats();
    Optional<IStats> devGetClassStats();

//    String devGetMeta();
//    T devSetMeta(String meta);
}
