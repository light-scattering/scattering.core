package eu.scattering.core.implementation.development.statistics;

import eu.scattering.core.design.development.statistics.Statistics;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.*;

public class StatisticsDefault implements Statistics {

    private class Record {
        @Getter @Setter private int iterations = 0;
        @Getter @Setter private final List<Integer> executionTimes = new ArrayList<>();
    }

    private boolean recordingEnabled = false;
    private final Map<String, Record> executionData = new HashMap<>();

    private StatisticsDefault() { }

    public static Statistics create() {

        return new StatisticsDefault();
    }

    @Override
    public Statistics clear() {

        executionData.clear();

        return this;
    }

    @Override
    public Statistics setEnabled() {

        recordingEnabled = true;

        return this;
    }

    @Override
    public Statistics setDisabled() {

        recordingEnabled = false;

        return this;
    }

    @Override
    public boolean isEnabled() {

        return recordingEnabled;
    }

    @Override
    public Set<String> getMethodNames() {

        return executionData.keySet();
    }

    @Override
    public Optional<List<Integer>> getExecutionTimes(String methodName) {

        if (methodName == null) {
            throw new NullPointerException("The method name cannot be null");
        }

        if (executionData.containsKey(methodName)) {
            return Optional.of(executionData.get(methodName).getExecutionTimes());
        }

        return Optional.empty();
    }

    @Override
    public Optional<Integer> getNumberOfIterations(String methodName) {

        if (methodName == null) {
            throw new NullPointerException("The method name cannot be null");
        }

        if (executionData.containsKey(methodName)) {
            return Optional.of(executionData.get(methodName).getIterations());
        }

        return Optional.empty();
    }

    @Override
    public Statistics recordEvent(String methodName, long methodExecutionTime) {

        if (methodName == null) {
            throw new NullPointerException("The method name cannot be null");
        }

        if (methodExecutionTime < 0) {
            throw new ArithmeticException("The execution time cannot be lower than zero");
        }

        if (!recordingEnabled) {
            return this;
        }

        Record record;
        if (executionData.containsKey(methodName)) {
            record = executionData.get(methodName);
        } else {
            record = new Record();
            executionData.put(methodName, record);
        }

        record.setIterations(record.getIterations() + 1);
        record.getExecutionTimes().add((int) methodExecutionTime);

        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Map<String, Record> devStatsSorted = new TreeMap<>(executionData);

        builder.append(LocalTime.now().toString()).append(" - registered methods: ").append(devStatsSorted.size()).append("\n");

        for (Map.Entry<String, Record> entry : devStatsSorted.entrySet()) {
            builder.append("- ").append(entry.getKey()).append(" / ").append(entry.getValue()).append("\n");
        }

        return builder.toString();
    }

}
