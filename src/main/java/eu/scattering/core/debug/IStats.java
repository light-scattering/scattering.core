package eu.scattering.core.debug;

import java.util.Optional;
import java.util.Set;

public interface IStats {

    void recordEvent(String methodName, long methodExecutionTime); // Cannot be lower than zero, NullPointerException

    void reset();

    void setSuspended(boolean suspend);
    boolean isSuspended();

    Set<String> getMethodNames();
    Optional<IStatsMethod> getMethod(String methodName); // NullPointerException
}
