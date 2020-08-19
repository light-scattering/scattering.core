package eu.scattering.core.logic.dev;

import eu.scattering.core.logic.dev.stats.Stats;

import java.util.Optional;

public interface Dev<T> {

    T devDescribe();
    T devDescribeStats();
    T devDescribeClassStats();

    Optional<Long> devGetNumberOfInstances();
    T devResetNumberOfInstances();

    Optional<Stats> devGetStats();
    Optional<Stats> devGetClassStats();

    String devGetMeta();
    T devSetMeta(String meta);
}
