package eu.scattering.core.debug;

import eu.scattering.core.debug.impl.Stats;

import java.util.Optional;

public interface IDebug<T> {

    T devDescribe();
    T devDescribe(String message);

    Optional<Long> devGetNumberOfInstances();
    Optional<IStats> devGetStats();
    Optional<IStats> devGetClassStats();
    T devDescribeStats();
    T devDescribeClassStats();

//    String devGetMeta();
//    T devSetMeta(String meta);

}
