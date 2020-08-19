package eu.scattering.core.impl.dev.stats;

import eu.scattering.core.Config;
import eu.scattering.core.logic.dev.stats.Stats;
import eu.scattering.core.logic.dev.stats.StatsMethod;
import eu.scattering.core.factory.DevFactory;

import java.time.LocalTime;
import java.util.*;

public class StatsDefault implements Stats {

    private Map<String, StatsMethod> stats;
    private boolean suspended;

    private StatsDefault(boolean global) {

        stats = new HashMap<>();

        if (global) {
            this.suspended = false;
        } else {
            this.suspended = Config.isDevObjectStatsSuspended();
        }
    }

    public static Stats create(boolean global) {

        return new StatsDefault(global);
    }

    @Override
    public Set<String> getMethodNames() {

        return stats.keySet();
    }

    @Override
    public Optional<StatsMethod> getMethod(String methodName) {

        if (methodName == null) {
            throw new NullPointerException("The method name cannot be null");
        }

        StatsMethod record = stats.get(methodName);

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

        Optional<StatsMethod> statsMethodOptional = getMethod(methodName);
        StatsMethod statsMethod;

        if (statsMethodOptional.isEmpty()) {
            statsMethod = DevFactory.getIStatsMethod();
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
        Map<String, StatsMethod> devStatsSorted = new TreeMap<>(stats);

        builder.append(LocalTime.now().toString() + " - registered methods: " + devStatsSorted.size() + "\n");

        for (Map.Entry<String, StatsMethod> entry : devStatsSorted.entrySet()) {
            builder.append("- " + entry.getKey() + " / " + entry.getValue() + "\n");
        }

        return builder.toString();
    }

}
