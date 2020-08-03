package eu.scattering.core.debug;

import java.util.Optional;
import java.util.Set;

public interface IStats {

    void recordEvent(String methodName, long methodExecutionTime);
    void recordForcedEvent(String methodName, long methodExecutionTime);

    void clear();
    void enable();
    void disable();

    Set<String> getMethodNames();
    Optional<IStatsMethod> getMethod(String methodName);
}
