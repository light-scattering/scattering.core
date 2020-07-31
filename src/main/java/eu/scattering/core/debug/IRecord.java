package eu.scattering.core.debug;

import eu.scattering.core.debug.dao.DevStats;

public interface IRecord {

    public void recordInstance(Class key);

    public void recordData(Object object, String methodName, long executionTime);

//    public DevStats getStat(Class key);
//
//    public DevStats getStat(Object key);
//
//    public void clearStat(Class key);
//
//    public void clearStat(Object key);

}
