package eu.scattering.core.test.design.development.statistics;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Statistics {

    boolean isEnabled();
    Statistics setEnabled(boolean enabled);

    Statistics reset();
    Statistics recordEvent(String methodName, long methodExecutionTime);
    
    Set<String> getMethodNames();

    Optional<Integer> getNumberOfIterations(String methodName);
    Optional<List<Integer>> getExecutionTimes(String methodName);
}
