package eu.scattering.core.dev.stats;

import java.util.Optional;
import java.util.Set;

public interface IStats {

    void recordEvent(String methodName, long methodExecutionTime);

    void reset();

    void setSuspended(boolean suspend);
    boolean isSuspended();

    Set<String> getMethodNames();
    Optional<IStatsMethod> getMethod(String methodName);
}
