package eu.scattering.core.debug.dao;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DevStatsRecord {

    @Getter private int numberOfIterations;
    @Getter private List<Long> executionTimes;

    public DevStatsRecord() {

        numberOfIterations = 0;
        executionTimes = new ArrayList<>();
    }

    public void update(long time) {

        numberOfIterations++;
        executionTimes.add(time);
    }

    public long getTimeTotal() {

        return executionTimes.stream().reduce(0L, (total, e) -> total + e);
    }

    public long getTimeAvg() {

        return getTimeTotal() / numberOfIterations;
    }

    public long getTimeMax() {

        return executionTimes.stream().max(Long::compare).get();
    }

    public long getTimeMin() {

        return executionTimes.stream().min(Long::compare).get();
    }

    @Override
    public String toString() {

        return "iterations " + getNumberOfIterations() + " , time avg " + getTimeAvg() + " [ms]"
                + " , time min " + getTimeMin() + " [ms]" + " , time max " + getTimeMax() + " [ms]";
    }

}
