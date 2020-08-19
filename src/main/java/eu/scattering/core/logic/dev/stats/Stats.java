package eu.scattering.core.logic.dev.stats;

import java.util.Optional;
import java.util.Set;

public interface Stats {

    void recordEvent(String methodName, long methodExecutionTime);

    void reset();

    void setSuspended(boolean suspend);
    boolean isSuspended();

    Set<String> getMethodNames();
    Optional<StatsMethod> getMethod(String methodName);
}
