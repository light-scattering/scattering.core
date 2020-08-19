package eu.scattering.core.implementation.development.statistics;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.development.statistics.MethodStatistics;
import eu.scattering.core.injection.DevelopmentFactory;

import java.time.LocalTime;
import java.util.*;

public class StatisticsDefault implements Statistics {

    private Map<String, MethodStatistics> stats;
    private boolean suspended;

    private StatisticsDefault(boolean global) {

        stats = new HashMap<>();

        if (global) {
            this.suspended = false;
        } else {
            this.suspended = Config.isDevObjectStatsSuspended();
        }
    }

    public static Statistics create(boolean global) {

        return new StatisticsDefault(global);
    }

    @Override
    public Set<String> getMethodNames() {

        return stats.keySet();
    }

    @Override
    public Optional<MethodStatistics> getMethod(String methodName) {

        if (methodName == null) {
            throw new NullPointerException("The method name cannot be null");
        }

        MethodStatistics record = stats.get(methodName);

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

        Optional<MethodStatistics> statsMethodOptional = getMethod(methodName);
        MethodStatistics statsMethod;

        if (statsMethodOptional.isEmpty()) {
            statsMethod = DevelopmentFactory.getIStatsMethod();
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
        Map<String, MethodStatistics> devStatsSorted = new TreeMap<>(stats);

        builder.append(LocalTime.now().toString()).append(" - registered methods: ").append(devStatsSorted.size()).append("\n");

        for (Map.Entry<String, MethodStatistics> entry : devStatsSorted.entrySet()) {
            builder.append("- ").append(entry.getKey()).append(" / ").append(entry.getValue()).append("\n");
        }

        return builder.toString();
    }

}
