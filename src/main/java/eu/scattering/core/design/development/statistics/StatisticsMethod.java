package eu.scattering.core.design.development.statistics;

import java.util.List;

public interface StatisticsMethod {

    void recordExecutionTime(long executionTime);

    List<Long> getExecutionTimes();
    int getNumberOfIterations();

    long getTimeTotal();
    long getTimeAvg();
    long getTimeMax();
    long getTimeMin();
}
