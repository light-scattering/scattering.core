package eu.scattering.core.implementation.development.statistics;

import eu.scattering.core.design.development.statistics.StatisticsMethod;

import java.util.ArrayList;
import java.util.List;

public class StatisticsMethodDefault implements StatisticsMethod {

    private int numberOfIterations;
    private List<Long> executionTimes;

    private StatisticsMethodDefault() {

        numberOfIterations = 0;
        executionTimes = new ArrayList<>();
    }

    public static StatisticsMethod create() {

        return new StatisticsMethodDefault();
    }

    public void recordExecutionTime(long executionTime) {

        if (executionTime < 0) {
            throw new ArithmeticException("The event time must be greater than zero");
        }

        numberOfIterations++;
        getExecutionTimes().add(executionTime);
    }

    @Override
    public List<Long> getExecutionTimes() {

        return executionTimes;
    }

    @Override
    public int getNumberOfIterations() {

        return numberOfIterations;
    }

    @Override
    public long getTimeTotal() {

        return getExecutionTimes().stream().reduce(0L, Long::sum);
    }

    @Override
    public long getTimeAvg() {

        return getNumberOfIterations() > 0 ? getTimeTotal() / getNumberOfIterations() : 0;
    }

    @Override
    public long getTimeMax() {

        return getExecutionTimes().stream().max(Long::compare).orElse(0L);
    }

    @Override
    public long getTimeMin() {

        return getExecutionTimes().stream().min(Long::compare).orElse(0L);
    }

    @Override
    public String toString() {

        return "iterations " + getNumberOfIterations() + ", time avg " + getTimeAvg() + " [ms]"
                + ", time min " + getTimeMin() + " [ms]" + ", time max " + getTimeMax() + " [ms]";
    }

}
