package eu.scattering.core.dev.stats;

import java.util.List;

public interface IStatsMethod {

    void recordExecutionTime(long executionTime);

    List<Long> getExecutionTimes();
    int getNumberOfIterations();

    long getTimeTotal();
    long getTimeAvg();
    long getTimeMax();
    long getTimeMin();
}
