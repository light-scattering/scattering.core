package eu.scattering.core.debug.dao;

import java.util.HashMap;
import java.util.Map;

public class Dev {

    private int numberOfInstances = 0;
    private DevStats statClass = new DevStats();
    private Map<Object, DevStats> statObject = new HashMap<>();

    public void incNumberOfInstances() {

        numberOfInstances++;
    }

    public int getNumberOfInstances() {

        return numberOfInstances;
    }

    public DevStatsRecord getStats(String methodName) {

        return statClass.getDevStatsRecord(methodName);
    }

    public DevStatsRecord getStats(Object object, String methodName) {
        DevStats element = statObject.get(object);

        if (element == null) {
            element = new DevStats();
            statObject.put(object, element);
        }

        return element.getDevStatsRecord(methodName);
    }

    @Override
    public String toString() {

        return statClass.toString();
    }

}
