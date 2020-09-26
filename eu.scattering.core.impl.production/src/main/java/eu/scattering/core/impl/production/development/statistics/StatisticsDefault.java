package eu.scattering.core.impl.production.development.statistics;

import eu.scattering.core.test.design.development.statistics.Statistics;

import java.time.LocalTime;
import java.util.*;

public class StatisticsDefault implements Statistics {

    private class Record {
        private int iterations = 0;
        private final List<Integer> executionTimes = new ArrayList<>();

        public int getIterations() {
            return iterations;
        }

        public void setIterations(int iterations) {
            this.iterations = iterations;
        }

        public List<Integer> getExecutionTimes() {
            return executionTimes;
        }
    }

    private boolean recordingEnabled = false;
    private final Map<String, Record> executionData = new HashMap<>();

    private StatisticsDefault() { }

    public static Statistics create() {

        return new StatisticsDefault();
    }

    @Override
    public Statistics reset() {

        executionData.clear();

        return this;
    }

    @Override
    public Statistics setEnabled(boolean enabled) {

        recordingEnabled = enabled;

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
            throw new IllegalArgumentException("The execution time cannot be lower than zero");
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
        Map<String, Record> devStatsSorted = new TreeMap<>(executionData);
        StringBuilder builder = new StringBuilder();

        builder
                .append("Registered methods: ").append(devStatsSorted.size()).append(" ")
                .append("(retrieval time - ").append(LocalTime.now().toString()).append("):")
                .append("\n");


        for (Map.Entry<String, Record> entry : devStatsSorted.entrySet()) {
            Integer iterations = entry.getValue().getIterations();
            List<Integer> executionTimes = entry.getValue().getExecutionTimes();

            int total = executionTimes.stream().reduce(0, (a, b) -> a + b);
            int average = total / iterations;
            int max = executionTimes.stream().max(Comparator.comparing(Integer::intValue)).get();
            int min = executionTimes.stream().min(Comparator.comparing(Integer::intValue)).get();

            builder.append("- ").append(entry.getKey()).append(" [").append(iterations).append("] - ")
                    .append("Total: ").append(total).append(" | ")
                    .append("Avg: ").append(average).append(" | ")
                    .append("Min: ").append(min).append(" | ")
                    .append("Max: ").append(max)
                    .append("\n");
        }

        return builder.toString();
    }

}
