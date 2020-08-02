package eu.scattering.core.debug.impl;

import eu.scattering.core.debug.IRecord;
import eu.scattering.core.debug.dao.Dev;
import eu.scattering.core.debug.dao.DevStats;
import eu.scattering.core.debug.dao.DevStatsRecord;

import java.util.HashMap;
import java.util.Map;

public class Record implements IRecord {

    private final Map<Class, Dev> stats = new HashMap<>();

    @Override
    public void recordInstance(Class key) {

        getStat(key).incNumberOfInstances();
    }

    @Override
    public void recordData(Object object, String methodName, long executionTime) {

        Dev dev = getStat(object.getClass());

        dev.getStats(methodName).update(executionTime);
        dev.getStats(object, methodName).update(executionTime);
    }

    @Override
    public Dev getStat(Class key) {

        //      System.out.println(key.toString());
        Dev dev = stats.get(key);
        // System.out.println(dev.toString());
        if (dev == null) {
            dev = new Dev();
            stats.put(key, dev);
        }
        //System.out.println(dev.toString());
        return dev;
    }
//
//    @Override
//    public DevStats getStat(Object object) {
//        return null;
//    }
//
//    @Override
//    public void clearStat() {
//
//    }
//
//    @Override
//    public void clearStat(Object object) {
//
//    }


}
