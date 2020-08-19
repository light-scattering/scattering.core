package eu.scattering.core.logic.dev.stats;

import java.util.List;

public interface StatsMethod {

    void recordExecutionTime(long executionTime);

    List<Long> getExecutionTimes();
    int getNumberOfIterations();

    long getTimeTotal();
    long getTimeAvg();
    long getTimeMax();
    long getTimeMin();
}
