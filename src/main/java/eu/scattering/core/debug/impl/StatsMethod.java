package eu.scattering.core.debug.impl;

import eu.scattering.core.debug.IStatsMethod;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StatsMethod implements IStatsMethod {

    private int numberOfIterations;
    private List<Long> executionTimes;
    private boolean isEnabled;

    private StatsMethod() {

        numberOfIterations = 0;
        executionTimes = new ArrayList<>();
        isEnabled = true;
    }

    public static IStatsMethod create() {

        return new StatsMethod();
    }

    @Override
    public void recordEvent(long executionTime) {

        if (isEnabled) {
            recordForcedEvent(executionTime);
        }
    }

    @Override
    public void recordForcedEvent(long executionTime) {

        numberOfIterations++;
        getExecutionTimes().add(executionTime);
    }

    @Override
    public void clear() {

        numberOfIterations = 0;
        getExecutionTimes().clear();
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
