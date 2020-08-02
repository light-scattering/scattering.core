package eu.scattering.core.debug.dao;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Dev {

    @Getter private int numberOfInstances = 0;
    private DevStats statClass = new DevStats();
    private Map<Object, DevStats> statObject = new HashMap<>();

    public void incNumberOfInstances() {

        numberOfInstances++;
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
        StringBuilder builder = new StringBuilder();

        builder.append("registered methods: " + statClass.getDevStats().size() + "\n");
        builder.append("registered objects: " + statObject.size() + " ");
        builder.append("(to see their statistics, you have to access them individually)\n");

        for (Map.Entry<String, DevStatsRecord> entry : statClass.getDevStats().entrySet()) {
            builder.append(" - " + entry.getKey() + " / " + entry.getValue() + "\n");
        }

        return builder.toString();
    }

}
