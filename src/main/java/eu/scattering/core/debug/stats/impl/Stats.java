package eu.scattering.core.debug.stats.impl;

import eu.scattering.core.Configuration;
import eu.scattering.core.debug.stats.IStats;
import eu.scattering.core.debug.stats.IStatsMethod;
import eu.scattering.core.factory.FactoryDebug;

import java.time.LocalTime;
import java.util.*;

public class Stats implements IStats {

    private Map<String, IStatsMethod> stats;
    private boolean suspended;

    private Stats(boolean global) {

        stats = new HashMap<>();

        if (global) {
            this.suspended = false;
        } else {
            this.suspended = Configuration.isDevObjectStatsSuspended();
        }
    }

    public static IStats create(boolean global) {

        return new Stats(global);
    }

    @Override
    public Set<String> getMethodNames() {

        return stats.keySet();
    }

    @Override
    public Optional<IStatsMethod> getMethod(String methodName) {

        if (methodName == null) {
            throw new NullPointerException("The method name cannot be null");
        }

        IStatsMethod record = stats.get(methodName);

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(record);
    }

    @Override
    public void recordEvent(String methodName, long methodExecutionTime) {

        if (methodName == null) {
            throw new NullPointerException("The method name cannot be null");
        }

        if (suspended) {
            return;
        }

        Optional<IStatsMethod> statsMethodOptional = getMethod(methodName);
        IStatsMethod statsMethod;

        if (statsMethodOptional.isEmpty()) {
            statsMethod = FactoryDebug.getIStatsMethod();
            stats.put(methodName, statsMethod);
        } else {
            statsMethod = statsMethodOptional.get();
        }

        statsMethod.recordExecutionTime(methodExecutionTime);
    }

    @Override
    public void reset() {

        stats.clear();
    }

    public void setSuspended(boolean suspend) {

        suspended = suspend;
    }

    @Override
    public boolean isSuspended() {

        return suspended;
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
