package eu.scattering.core.impl.dev.stats;

import eu.scattering.core.logic.dev.stats.StatsMethod;

import java.util.ArrayList;
import java.util.List;

public class StatsMethodDefault implements StatsMethod {

    private int numberOfIterations;
    private List<Long> executionTimes;

    private StatsMethodDefault() {

        numberOfIterations = 0;
        executionTimes = new ArrayList<>();
    }

    public static StatsMethod create() {

        return new StatsMethodDefault();
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
