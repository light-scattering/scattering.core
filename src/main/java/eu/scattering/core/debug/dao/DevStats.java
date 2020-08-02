package eu.scattering.core.debug.dao;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class DevStats {

    @Getter private Map<String, DevStatsRecord> devStats;

    public DevStats() {

        devStats = new HashMap<>();
    }

    public DevStatsRecord getDevStatsRecord(String methodName) {
        DevStatsRecord record = devStats.get(methodName);

        if (record == null) {
            record = new DevStatsRecord();
            devStats.put(methodName, record);
        }

        return record;
    }

    public void recordEvent(String methodName, long executionTime) {

        getDevStatsRecord(methodName).update(executionTime);
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("registered methods: " + devStats.size() + "\n");

        for (Map.Entry<String, DevStatsRecord> entry : devStats.entrySet()) {
            builder.append(entry.getKey() + " / " + entry.getValue() + "\n");
        }

        return builder.toString();
    }

}
