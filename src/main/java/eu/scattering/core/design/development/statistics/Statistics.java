package eu.scattering.core.design.development.statistics;

import java.util.Optional;
import java.util.Set;

public interface Statistics {

    Statistics recordEvent(String methodName, long methodExecutionTime);

    Statistics reset();

    Statistics setActive(boolean suspend);
    boolean isActive();

    Set<String> getMethodNames();
    Optional<StatisticsMethod> getMethod(String methodName);
}
