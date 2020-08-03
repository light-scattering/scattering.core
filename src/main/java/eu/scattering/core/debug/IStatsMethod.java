package eu.scattering.core.debug;

import java.util.List;

public interface IStatsMethod {

    void recordEvent(long executionTime);
    void recordForcedEvent(long executionTime);

    void clear();
    void enable();
    void disable();

    List<Long> getExecutionTimes();
    int getNumberOfIterations();

    long getTimeTotal();
    long getTimeAvg();
    long getTimeMax();
    long getTimeMin();
}
