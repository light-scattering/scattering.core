package eu.scattering.core.debug.dao;

import java.util.HashMap;
import java.util.Map;

public class DevStats {

    private Map<String, DevStatsRecord> devStats;

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

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, DevStatsRecord> entry : devStats.entrySet()) {
            builder.append(entry.getKey() + " / " + entry.getValue());
        }

        return builder.toString();
    }

}
