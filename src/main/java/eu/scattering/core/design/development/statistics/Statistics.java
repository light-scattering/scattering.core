package eu.scattering.core.design.development.statistics;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Statistics {

    Statistics clear();
    Statistics recordEvent(String methodName, long methodExecutionTime);

    boolean isEnabled();
    Statistics setEnabled(); // add flag
    Statistics setDisabled();// delete
    
    Set<String> getMethodNames();

    Optional<Integer> getNumberOfIterations(String methodName);
    Optional<List<Integer>> getExecutionTimes(String methodName);
}
