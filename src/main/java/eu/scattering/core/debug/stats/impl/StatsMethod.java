package eu.scattering.core.debug.stats.impl;

import eu.scattering.core.debug.stats.IStatsMethod;

import java.util.ArrayList;
import java.util.List;

public class StatsMethod implements IStatsMethod {

    private int numberOfIterations;
    private List<Long> executionTimes;

    private StatsMethod() {

        numberOfIterations = 0;
        executionTimes = new ArrayList<>();
    }

    public static IStatsMethod create() {

        return new StatsMethod();
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

        return getExecutionTimes().stream().reduce(0L, (total, e) -> total + e);
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
