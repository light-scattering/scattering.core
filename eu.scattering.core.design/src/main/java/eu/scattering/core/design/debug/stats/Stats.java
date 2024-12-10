package eu.scattering.core.design.debug.stats;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Stats {

    boolean isEnabled();
    Stats setEnabled(boolean enabled);

    Stats reset();
    Stats recordEvent(String methodName, long methodExecutionTime);
    
    Set<String> getMethodNames();

    Optional<Integer> getNumberOfIterations(String methodName);
    Optional<List<Integer>> getExecutionTimes(String methodName);
}
