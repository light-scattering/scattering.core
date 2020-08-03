package eu.scattering.core.debug.impl;

import eu.scattering.core.debug.IStats;
import eu.scattering.core.debug.IStatsMethod;
import eu.scattering.core.factory.FactoryDebug;

import java.time.LocalTime;
import java.util.*;

public class Stats implements IStats {

    private Map<String, IStatsMethod> stats;
    private boolean isEnabled;

    private Stats() {

        stats = new HashMap<>();
        isEnabled = true;
    }

    public static IStats create() {

        return new Stats();
    }

    @Override
    public Set<String> getMethodNames() {

        return stats.keySet();
    }

    @Override
    public Optional<IStatsMethod> getMethod(String methodName) {
        IStatsMethod record = stats.get(methodName);

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(record);
    }

    @Override
    public void recordEvent(String methodName, long methodExecutionTime) {

        if (isEnabled) {
            recordForcedEvent(methodName, methodExecutionTime);
        }
    }

    @Override
    public void recordForcedEvent(String methodName, long methodExecutionTime) {
        Optional<IStatsMethod> statsMethodOptional = getMethod(methodName);
        IStatsMethod statsMethod;

        if (statsMethodOptional.isEmpty()) {
            statsMethod = FactoryDebug.getIStatsMethod();
            stats.put(methodName, statsMethod);
        } else {
            statsMethod = statsMethodOptional.get();
        }

        statsMethod.recordEvent(methodExecutionTime);
    }

    @Override
    public void clear() {

        stats.clear();
    }

    @Override
    public void enable() {

        isEnabled = true;
    }

    @Override
    public void disable() {

        isEnabled = false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Map<String, IStatsMethod> devStatsSorted = new TreeMap<>(stats);

        builder.append(LocalTime.now().toString() + " - registered methods: " + devStatsSorted.size() + "\n");

        for (Map.Entry<String, IStatsMethod> entry : devStatsSorted.entrySet()) {
            builder.append("- " + entry.getKey() + " / " + entry.getValue() + "\n");
        }

        return builder.toString();
    }

}
