package eu.scattering.core.debug.impl;

import eu.scattering.core.debug.IStats;
import eu.scattering.core.debug.IStatsMethod;
import eu.scattering.core.factory.FactoryDebug;

import java.time.LocalTime;
import java.util.*;

public class Stats implements IStats {

    private Map<String, IStatsMethod> stats;
    private boolean isSuspended;

    private Stats(boolean suspend) {

        stats = new HashMap<>();
        this.isSuspended = suspend;
    }

    public static IStats create(boolean suspend) {

        return new Stats(suspend);
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

        if (isSuspended) {
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

        statsMethod.recordEvent(methodExecutionTime);
    }

    @Override
    public void reset() {

        stats.clear();
    }

    public void setSuspended(boolean suspend) {

        isSuspended = suspend;
    }

    @Override
    public boolean isSuspended() {

        return isSuspended;
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
