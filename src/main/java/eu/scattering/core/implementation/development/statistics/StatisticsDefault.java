package eu.scattering.core.implementation.development.statistics;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.development.statistics.StatisticsMethod;

import java.time.LocalTime;
import java.util.*;

import static eu.scattering.core.Config.statisticsFactory;

public class StatisticsDefault implements Statistics {

    private Map<String, StatisticsMethod> statistics;
    private boolean active;

    private StatisticsDefault() {

        statistics = new HashMap<>();
        active = false;
    }

    public static Statistics create() {

        return new StatisticsDefault();
    }

    @Override
    public Statistics reset() {

        statistics.clear();

        return this;
    }

    @Override
    public Statistics setEnabled() {

        active = true;

        return this;
    }

    @Override
    public Statistics setDisabled() {

        active = false;

        return this;
    }

    @Override
    public boolean isEnabled() {

        return active;
    }

    @Override
    public Set<String> getRegisteredMethodNames() {

        return statistics.keySet();
    }

    @Override
    public Optional<StatisticsMethod> getRegisteredMethod(String methodName) {

        if (methodName == null) {
            throw new NullPointerException("The method name cannot be null");
        }

        StatisticsMethod record = statistics.get(methodName);

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(record);
    }

    @Override
    public Statistics recordEvent(String methodName, long methodExecutionTime) {

        if (methodName == null) {
            throw new NullPointerException("The method name cannot be null");
        }

        if (!active) {
            return this;
        }

        Optional<StatisticsMethod> statsMethodOptional = getRegisteredMethod(methodName);
        StatisticsMethod statsMethod;

        if (statsMethodOptional.isEmpty()) {
            statsMethod = statisticsFactory.getStatisticsMethod();
            statistics.put(methodName, statsMethod);
        } else {
            statsMethod = statsMethodOptional.get();
        }

        statsMethod.recordExecutionTime(methodExecutionTime);

        return this;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Map<String, StatisticsMethod> devStatsSorted = new TreeMap<>(statistics);

        builder.append(LocalTime.now().toString()).append(" - registered methods: ").append(devStatsSorted.size()).append("\n");

        for (Map.Entry<String, StatisticsMethod> entry : devStatsSorted.entrySet()) {
            builder.append("- ").append(entry.getKey()).append(" / ").append(entry.getValue()).append("\n");
        }

        return builder.toString();
    }

}
