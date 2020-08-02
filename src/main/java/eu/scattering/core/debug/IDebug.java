package eu.scattering.core.debug;

import eu.scattering.core.debug.dao.DevStats;

import java.util.Optional;

public interface IDebug<T> {

    T devDescribe();
    T devDescribe(String message);
    T devDescribeStats();

    Optional<DevStats> devGetStats();
    Optional<Long> devGetNumberOfInstances();

//    T devLog();
//    T devLog(String message);

//    String devGetMeta();
//    T devSetMeta(String meta);

}
