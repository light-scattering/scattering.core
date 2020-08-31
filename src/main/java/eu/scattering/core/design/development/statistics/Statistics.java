package eu.scattering.core.design.development.statistics;

import java.util.Optional;
import java.util.Set;

public interface Statistics {

    Statistics reset();

    Statistics setEnabled();
    Statistics setDisabled();

    boolean isEnabled();

    Statistics recordEvent(String methodName, long methodExecutionTime);

    Set<String> getRegisteredMethodNames();
    Optional<StatisticsMethod> getRegisteredMethod(String methodName);
}
