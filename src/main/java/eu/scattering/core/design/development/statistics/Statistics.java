package eu.scattering.core.design.development.statistics;

import java.util.Optional;
import java.util.Set;

public interface Statistics {

    void recordEvent(String methodName, long methodExecutionTime);

    void reset();

    void setSuspended(boolean suspend);
    boolean isSuspended();

    Set<String> getMethodNames();
    Optional<StatisticsMethod> getMethod(String methodName);
}
